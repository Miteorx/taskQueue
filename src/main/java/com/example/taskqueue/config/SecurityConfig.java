package com.example.taskqueue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authRequests -> authRequests
            .requestMatchers("/login").permitAll()
            .requestMatchers("/task/create").hasAuthority("PRIV_CREATE_TASK")
            .requestMatchers("/task/mytasks").hasAuthority("PRIV_GET_TASK")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/")
        )
        .build();
  }
}
