package com.example.announcmentapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
public class SecurityConfig {
    private final CustomFilter customFilter;

    private final CustomEntryPoint customEntryPoint;

    public SecurityConfig(CustomFilter customFilter, CustomEntryPoint customEntryPoint) {
        this.customFilter = customFilter;
        this.customEntryPoint = customEntryPoint;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers(POST, "api/v1/accounts/security/**").permitAll()
                                .requestMatchers(GET, "api/v1/accounts/security/*").permitAll()
                                .requestMatchers(GET, "api/v1/accounts/**").permitAll()
                                .requestMatchers(POST, "api/v1/accounts/security").hasAnyAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(basic -> basic.authenticationEntryPoint(customEntryPoint));

        return httpSecurity.build();
    }
}

