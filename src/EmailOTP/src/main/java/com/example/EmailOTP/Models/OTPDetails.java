package com.example.EmailOTP.Models;

import java.util.*; 

public class OTPDetails
{
    private String email;
    private String otp;
    private Date generatedTime;
    private int attempts;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String _email)
    {
        this.email = _email;
    }

    public String getOTP()
    {
        return otp;
    }

    public void setOTP(String _otp)
    {
        this.otp = _otp;
    }

    public Date getGeneratedTime()
    {
        return generatedTime;
    }

    public void setGeneratedTime(Date _genTime)
    {
        this.generatedTime = _genTime;
    }

    public int getAttempts()
    {
        return attempts;
    }

    public void setAttempts(int _attempts)
    {
        this.attempts = _attempts;
    }
}