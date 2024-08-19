package com.example.EmailOTP.Services;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OTPService implements IOTPService
{
    public String GenerateOTP()
    {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return Integer.toString(otp);
    }
}