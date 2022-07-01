package com.nextgendevs.hanchor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextgendevs.hanchor.SpringApplicationContext;
import com.nextgendevs.hanchor.service.UserService;
import com.nextgendevs.hanchor.shared.dto.UserDto;
import com.nextgendevs.hanchor.ui.model.request.LoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestModel credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestModel.class);

            String username = "";
            if (credentials.getEmail() == null) {
                username = credentials.getId();
            }
            if (credentials.getId() == null ) {
                username = credentials.getEmail();
            }

            //look up user in our database
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, credentials.getPassword(), new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //once request is successful
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

//        String username = ((User) authResult.getPrincipal()).getUsername();
        String username = ((UserPrinciple) authResult.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

        UserDto user = userService.getUserLoginDetails(username);

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        response.addHeader(SecurityConstants.USERID, user.getUserId());
        response.addHeader(SecurityConstants.USERNAME, user.getUsername());
        response.addHeader(SecurityConstants.FIRST_NAME, user.getFirstname());
        response.addHeader(SecurityConstants.LAST_NAME, user.getLastname());
        response.addHeader(SecurityConstants.EMAIL, user.getEmail());
        response.addHeader(SecurityConstants.EMAIL_VERIFICATION_STATUS, String.valueOf(user.getEmailVerificationStatus()));

    }

}