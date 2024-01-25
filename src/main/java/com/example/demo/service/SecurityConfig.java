package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login")
                        .permitAll()
                        .requestMatchers(
                                "/",
                                "/employees/**",
                                "/absences/**"
                                ).hasAnyRole("admin","rrhh")
                        .requestMatchers(
                                "/users/**"
                                ).hasAnyRole("admin")
                        .requestMatchers(
                                "/css/**",
                                "/images/**",
                                "/js/**",
                                "/lib/**",
                                "/mail/**",
                                "/scss/**",
                                "/vendor/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/login")
                        .successForwardUrl("/auth")
                        .permitAll()
                ).logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/close")
                        .permitAll()
                );
        return httpSecurity.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
