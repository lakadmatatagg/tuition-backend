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
                                "http://172.20.30.150:4200",
                                "https://stg.nvis.jpph.gov.my",
                                "https://nvis.jpph.gov.my",
                                "https://bpm.nvis.jpph.gov.my", // prod
                                "https://bpmv2.nvis.jpph.gov.my", // stg
                                "https://qs.nvis.jpph.gov.my",
                                "https://qs-bpm.nvis.jpph.gov.my",
                                "https://www.nvis.jpph.gov.my",
                                "https://adminmod.nvis.jpph.gov.my",
                                "https://adminsvc.nvis.jpph.gov.my",
                                "https://devbpm.nvis.jpph.gov.my",
                                "https://sso.nvis.jpph.gov.my/",
                                "https://adminmod.nvis.jpph.gov.my",
                                "https://sc.nvis.jpph.gov.my",
                                "http://localhost:3000",
                                "http://10.23.171.95:82"
                        )
                        .allowCredentials(true);
            }
        };
    }
}
