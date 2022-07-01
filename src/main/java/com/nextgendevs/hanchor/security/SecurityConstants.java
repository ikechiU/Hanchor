package com.nextgendevs.hanchor.security;


import com.nextgendevs.hanchor.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; //10 days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; //1 hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String FIRST_NAME = "Firstname";
    public static final String LAST_NAME = "Lastname";
    public static final String EMAIL = "Email";
    public static final String USERID = "UserId";
    public static final String USERNAME = "Username";
    public static final String REGISTER_USER = "/hanchor/v1/users";
    public static final String REGISTER_USER_2 = "/hanchor/v2/users";
    public static final String EMAIL_VERIFICATION_URL = "/hanchor/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/hanchor/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/hanchor/password-reset";
    public static final String EMAIL_VERIFICATION_STATUS = "EmailVerified";
    public static final String H2_CONSOLE = "/h2-console/**";
//    public static final String TOKEN_SECRET = "jf9i4jgu8nfl0"; added in application properties

    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
