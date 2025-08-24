package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();// we used this auto config method to get the authentication manager with all the providers we have configured
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // STEP 4A: Configure URL-based authorization
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints - no authentication required
                        .requestMatchers("/", "/home", "/about").permitAll()
                        .requestMatchers("api/hello").permitAll()
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()

                        // API endpoints for registration (public)
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                        // Admin-only endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // User endpoints - require USER or ADMIN role
                        .requestMatchers("/dashboard/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/tasks/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/categories/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/tasks/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/categories/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")                    // Custom login page
                        .loginProcessingUrl("/perform_login")   // Where form submits to
                        .defaultSuccessUrl("/dashboard", true)  // Redirect after successful login
                        .failureUrl("/login?error=true")        // Redirect after failed login
                        .usernameParameter("username")          // Form field name for username
                        .passwordParameter("password")          // Form field name for password
                        .permitAll()
                )
                // STEP 4C: Configure Logout
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")           // URL to trigger logout
                        .logoutSuccessUrl("/login?logout=true") // Redirect after logout
                        .deleteCookies("JSESSIONID")            // Clear session cookie
                        .invalidateHttpSession(true)            // Invalidate session
                        .clearAuthentication(true)              // Clear authentication
                        .permitAll()
                )
                // STEP 4D: Configure Session Management
                .sessionManagement(session -> {
                    session.maximumSessions(1)
                            .maxSessionsPreventsLogin(false);
                    session.sessionFixation().changeSessionId();
                })
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")     // Disable CSRF for API endpoints
                );

        return http.build();
    }
}
