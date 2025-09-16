package com.bahaa.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {
    private final UserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public WebSecurityConfig(UserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Session Management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                // Authorization
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll() // registration

                        // Admin endpoints
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN") // list all users
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users").hasRole("ADMIN")

                        // Authenticated users
                        .requestMatchers("/tasks/**").authenticated()
                        .requestMatchers("/categories/**").authenticated()

                        // Everything else
                        .anyRequest().authenticated()
                )

                // Form Login (you can remove if you only use REST + JWT later)
                .formLogin(Customizer.withDefaults())

                // Logout
                .logout(Customizer.withDefaults())

                // CSRF disabled for REST (important for Postman/Angular/React clients)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    protected SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
