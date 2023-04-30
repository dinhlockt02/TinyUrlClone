package com.example.tinyurlclone.security;

import com.example.tinyurlclone.security.model.User;
import com.example.tinyurlclone.user.repositoy.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtRefreshTokenAuthenticationFilter jwtRefreshTokenAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .authorizeHttpRequests()
                        .requestMatchers("/api/v1/auth/**")
                            .permitAll()
                        .requestMatchers("/error**")
                            .permitAll()
                        .requestMatchers("/api/v1/url/**")
                            .permitAll()
                        .anyRequest()
                            .authenticated()
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
                    .addFilterBefore(jwtRefreshTokenAuthenticationFilter, JwtAuthenticationFilter.class)
                .csrf()
                    .disable()
                .build();
    }
}
