package com.tigasatutiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class The313App {
    public static void main(String[] args) {
        SpringApplication.run(The313App.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins(
                    "http://localhost:4200",
                    "http://localhost:4201",
                    "http://localhost:4202",
                    "http://localhost:3000",
                    "https://auth.tigasatutiga.com",
                    "https://tuitionez-filer.tigasatutiga.com",
                    "https://tuitionez-admin.tigasatutiga.com"
                )
                .allowCredentials(true);
            }
        };
    }
}
