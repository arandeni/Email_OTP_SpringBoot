package com.example.EmailOTP.Services;

import java.util.regex.*;
import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService implements IEmailService
{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    //@Autowired
    //private JavaMailSender javaMailSender;
    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    //Check the email is a valid email or not
    public boolean IsEmalValid(String email)
    {
        Matcher matcher = EmailService.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    //Send OTP to the given email
    public boolean SendEmail(String to, String subject, String message)
    {
        /*SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(to);
        msg.setText(message);
        this.mailSender.send(msg);*/
       /*  MimeMessage msg = javaMailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(message, true);
	        javaMailSender.send(msg);*/
        return true;
    }
}