package com.example.EmailOTP.Services;

public interface IEmailService
{
    boolean IsEmalValid(String email);
    boolean SendEmail(String to, String subject, String messages);
}