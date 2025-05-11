package com.crane.cpb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Xanthos
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // 必须明确指定前端地址
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true) // 允许凭据
                .maxAge(3600);
    }
}