package com.example.EmailOTP.Services;

import java.util.regex.*;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;


@Service
public class EmailService implements IEmailService
{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public boolean IsEmalValid(String email)
    {
        Matcher matcher = EmailService.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    public boolean SendEmail(String email, String body)
    {
        return true;
    }
}