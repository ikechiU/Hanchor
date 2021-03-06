package com.nextgendevs.hanchor.security;

import com.nextgendevs.hanchor.io.repository.UserRepository;
import com.nextgendevs.hanchor.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class)//add your custom CorsFilter
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_USER)
                .permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_USER_2)
                .permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.EMAIL_VERIFICATION_URL)
                .permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
                .permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
                .permitAll()
//                .antMatchers(SecurityConstants.H2_CONSOLE)
//                .permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/")//GET mapping re-direct to swagger-ui/index.html
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().addFilter(getAuthenticationFilter()) //adding the authentication customized filter
                .addFilter(new AuthorizationFilter(authenticationManager(), userRepository))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //http sessions or be created not to be cached - reauthorize

//        http.headers().defaultsDisabled().cacheControl(); //to be commented out once app goes live - h2 purpose
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/hanchor/login");
        return filter;
    }

    @Bean
    CorsFilter corsFilter() {
        return new CorsFilter();
    }


}