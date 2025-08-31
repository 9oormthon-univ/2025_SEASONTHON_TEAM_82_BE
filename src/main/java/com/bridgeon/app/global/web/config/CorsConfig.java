package com.bridgeon.app.global.web.config;

import com.bridgeon.app.global.web.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig {

    private final CorsProperties corsProperties;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration global = new CorsConfiguration();

        if (!corsProperties.getAllowedOriginPatterns().isEmpty()) {
            global.setAllowedOrigins(corsProperties.getAllowedOriginPatterns());
        }

        global.setAllowedMethods(corsProperties.getAllowedMethods());
        global.setAllowedHeaders(corsProperties.getAllowedHeaders());
        global.setAllowCredentials(corsProperties.isAllowCredentials());
        global.setMaxAge(corsProperties.getMaxAge());

        CorsConfiguration swagger = new CorsConfiguration(global);
        if (!corsProperties.getAllowedOriginPatterns().isEmpty()) {
            swagger.setAllowedOrigins(corsProperties.getSwaggerAllowedOriginPatterns());
        } else {
            swagger.setAllowedOrigins(List.of("*"));
        }

        swagger.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        swagger.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", global);
        source.registerCorsConfiguration("/swagger-ui/**", swagger);
        source.registerCorsConfiguration("/v3/api-docs/**", swagger);

        return source;
    }
}
