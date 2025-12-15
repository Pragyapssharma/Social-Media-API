package com.watershedmahotsav.socialmediaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.watershedmahotsav.socialmediaapi")
public class SocialMediaApiApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SocialMediaApiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApiApplication.class, args);
        System.out.println("Social Media API Started Successfully!");
    }
}