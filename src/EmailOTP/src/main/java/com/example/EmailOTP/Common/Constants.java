package com.example.EmailOTP.Common;

public class Constants
{
    public static final String STATUS_EMAIL_OK = "email containing OTP has been sent successfully";
    public static final String STATUS_EMAIL_FAIL = "email address does not exist or sending to the email has failed";
    public static final String STATUS_EMAIL_INVALID = "email address is invalid";
    public static final String STATUS_EMAIL_OTP_SENT = "OTP has been already sent to this email address";
    public static final String STATUS_OTP_OK = "OTP is valid and checked";
    public static final String STATUS_OTP_INVALID = "OTP is invalid";
    public static final String STATUS_OTP_INVALID_EMAIL = "email is invalid";
    public static final String STATUS_OTP_FAIL = "OTP is wrong after 10 tries";
    public static final String STATUS_OTP_TIMEOUT = "timeout after 1 min";
    public static final String STATUS_OTP_SESSION_EXPIRED = "session has been expired";
    public static final String INTERNAL_SERVER_ERROR = "Error occurred while processing your request";
    public static final int MAX_ATTEMPTS = 10;
    public static final int OTP_VALIDITY = 60;
    public static final String DOMAIN = "dso.org.sg";
}