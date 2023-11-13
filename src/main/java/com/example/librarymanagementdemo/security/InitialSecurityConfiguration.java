package com.example.librarymanagementdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class InitialSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //Will be changed later on

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/librarybranches").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/librarybranches/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/librarybranches").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/librarybranches/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/librarybranches").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/librarybranches/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/libraryusers").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/libraryusers/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/libraryusers").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/libraryusers").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/libraryusers/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/authors").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/authors/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/authors").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/authors").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/authors/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/books").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/books/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/books").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/books").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/checkouts").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/checkouts/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/checkouts").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/checkouts").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/checkouts/**").authenticated()
        );

        //use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());

        //disable Cross Site Request Forgery (CSRF)
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
