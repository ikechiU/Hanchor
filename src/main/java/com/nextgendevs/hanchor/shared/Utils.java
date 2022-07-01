package com.nextgendevs.hanchor.shared;

import com.nextgendevs.hanchor.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public String generateImageId(int length) {
        return generateRandomString(length);
    }

    public String generateFolderPath(int length) {
        return generateRandomString(length);
    }

    public static boolean hasTokenExpired(String token) {

        boolean returnValue = false;

        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody();

            Date tokenExpirationDate = claims.getExpiration();
            Date todayDate = new Date();

            returnValue = tokenExpirationDate.before(todayDate);
        } catch (ExpiredJwtException ex) {
            returnValue =  true;
        }

        return returnValue;
    }

    public String generateEmailVerificationToken(String userId) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
        return token;
    }

    public String generatePasswordResetToken(String userId)
    {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
        return token;
    }

    private String getLowerCaseText(String text) {
        return text.toLowerCase();
    }

    public String getFirstNameDotLastName(String firstname, String lastname) {
        return (getLowerCaseText(firstname) + "." + getLowerCaseText(lastname));
    }

    public boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public String getCapitalizeName(String name) {
        String returnValue;

        if (name.length() == 1) {
            returnValue =  name.toUpperCase();
        } else {
            try { // We can face index out of bound exception if the string is null
                returnValue = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            }catch (Exception e){
                returnValue = e.getMessage();
            }
        }

        return returnValue;
    }

}
