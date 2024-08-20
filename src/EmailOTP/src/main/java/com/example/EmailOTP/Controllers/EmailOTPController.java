package com.example.EmailOTP.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.EmailOTP.Common.Constants;
import com.example.EmailOTP.Services.IEmailService;
import com.example.EmailOTP.Services.IOTPService;

import Models.OTPDetails;

import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/EmailOTP")
public class EmailOTPController
{
    @Autowired
    private final IOTPService _otpService;

    @Autowired
    private final IEmailService _emailService;
    
    private LoadingCache<String, OTPDetails> _otpCache;

    @Autowired
    public EmailOTPController(IOTPService otpService, IEmailService emailService)
    {
        this._otpService = otpService;
        this._emailService = emailService;
        _otpCache = CacheBuilder.newBuilder().
            expireAfterWrite(4, TimeUnit.MINUTES).build(new CacheLoader<String,OTPDetails>() {
                public OTPDetails load(String key)
                {
                    return new OTPDetails();
                }
            });
    }

    //Generate OTP and send it to the given user
    @RequestMapping(value ="/generate_OTP_email/{user_email}", method = RequestMethod.POST)
    public ResponseEntity<String> generate_OTP_email(@PathVariable String user_email)
    {
        try
        {
            if(!_emailService.IsEmalValid(user_email))
            {
                return new ResponseEntity<>(Constants.STATUS_EMAIL_INVALID, HttpStatus.BAD_REQUEST);
            }
            else if(!user_email.endsWith(Constants.DOMAIN))
            {
                return new ResponseEntity<>(Constants.STATUS_EMAIL_FAIL, HttpStatus.BAD_REQUEST);
            }
            else
            {
                String otpCode = _otpService.GenerateOTP();
                OTPDetails otpDetails = new OTPDetails();
                otpDetails.setEmail(user_email);
                otpDetails.setOTP(otpCode);
                otpDetails.setAttempts(0);
                Date generatedTime = new Date();
                otpDetails.setGeneratedTime(generatedTime);
                
                _otpCache.put(user_email, otpDetails);

                String email_body = "You OTP Code is " + otpCode + ". The code is valid for 1 minute";

                //Send OTP code to the given email address
                boolean isEmailSent = _emailService.SendEmail(user_email, "OTP", email_body);
                if(isEmailSent)
                {
                    return new ResponseEntity<>(Constants.STATUS_EMAIL_OK, HttpStatus.OK);
                }
                else
                {
                    return new ResponseEntity<>(Constants.STATUS_EMAIL_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        catch(RuntimeException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //Validate OTP
    @RequestMapping(value ="/check_OTP/{email}/{otp}", method = RequestMethod.POST)
    public ResponseEntity<String> check_OTP(@PathVariable String email, @PathVariable String otp)
    {
        try
        {
            OTPDetails _otpDetails = _otpCache.get(email);
            if(_otpDetails != null)
            {
                long diff = (new Date().getTime() - _otpDetails.getGeneratedTime().getTime())/1000;

                if(diff < Constants.OTP_VALIDITY)
                {
                    if(_otpDetails.getAttempts() < Constants.MAX_ATTEMPTS)
                    {
                        if(_otpDetails.getOTP().equals(otp))
                        {
                            return new ResponseEntity<>(Constants.STATUS_OTP_OK, HttpStatus.OK);
                        }
                        else
                        {
                            int attempts = _otpDetails.getAttempts();
                            attempts++;
                            _otpDetails.setAttempts(attempts);
                            _otpCache.put(email, _otpDetails);
                            return new ResponseEntity<>(Constants.STATUS_OTP_INVALID, HttpStatus.NOT_ACCEPTABLE);
                        }
                    }
                    else
                    {
                        return new ResponseEntity<>(Constants.STATUS_OTP_FAIL, HttpStatus.GATEWAY_TIMEOUT);
                    }
                }
                else
                {
                    return new ResponseEntity<>(Constants.STATUS_OTP_TIMEOUT, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            else
            {
                return new ResponseEntity<>(Constants.STATUS_OTP_SESSION_EXPIRED, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch(ExecutionException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch(RuntimeException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}