package com.nextgendevs.hanchor;

import com.cloudinary.Cloudinary;
import com.nextgendevs.hanchor.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@SpringBootApplication
public class HanchorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanchorApplication.class, args);
    }

    @PostConstruct
    void setUTCTimezone(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean(name="AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", "***cloud_name***");
        config.put("api_key", "***accessKeyId***");
        config.put("api_secret", "***api_secret***");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }

}
