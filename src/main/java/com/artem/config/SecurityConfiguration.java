package com.artem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.artem.database.entity.Role.MANAGER;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
                        .antMatchers("/login", "/products", "/customers/registration", "/customers/create", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .antMatchers("/customers/{\\d+}/delete", "/customers").hasAuthority(MANAGER.getAuthority())
                        .antMatchers("/products/{\\d+}/**").hasAuthority(MANAGER.getAuthority())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/products"))
                .build();
    }
}