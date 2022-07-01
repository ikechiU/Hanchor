package com.nextgendevs.hanchor.security;

import com.nextgendevs.hanchor.io.entity.UserEntity;
import com.nextgendevs.hanchor.io.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authManager, UserRepository userRepository) {
        super(authManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            assert authentication != null;
            if (authentication.isAuthenticated())

                SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (JwtException ex) {
            System.out.println("JwtException: " + ex.getMessage());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {

            token = token.replace(SecurityConstants.TOKEN_PREFIX, ""); //remove "Bearer " we only need token secret

            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            UserEntity userEntity = new UserEntity();

            if (user != null) {
                if (user.contains("@")){
                    userEntity = userRepository.findUserEntitiesByEmail(user);
                } else {
                   userEntity = userRepository.findUserEntitiesByUserId(user);
                }

                //return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                UserPrinciple userPrinciple = new UserPrinciple(userEntity, user);
                return new UsernamePasswordAuthenticationToken(userPrinciple, null, userPrinciple.getAuthorities());
            }

            return null;
        }

        return null;
    }

}
