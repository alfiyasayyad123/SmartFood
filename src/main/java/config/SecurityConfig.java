package com.smartfood.backend.config;

import com.smartfood.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // ==============================
                        // PUBLIC WEB PAGES
                        // ==============================

                        .requestMatchers(
                                "/",
                                "/home",
                                "/login",
                                "/register",
                                "/restaurant",
                                "/cart",
                                "/order-confirmation",
                                "/order-tracking",
                                "/admin",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        // ==============================
                        // PUBLIC USER APIs
                        // ==============================

                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login"
                        ).permitAll()

                        // ==============================
                        // FOOD APIs
                        // ==============================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/foods/**"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/foods/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RESTAURANT"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/foods/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RESTAURANT"
                        )

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/foods/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RESTAURANT"
                        )

                        // ==============================
                        // RESTAURANT APIs
                        // ==============================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/restaurants/**"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/restaurants/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/restaurants/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RESTAURANT"
                        )

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/restaurants/**"
                        ).hasRole("ADMIN")

                        // ==============================
                        // CART
                        // ==============================

                        .requestMatchers(
                                "/api/cart/**"
                        ).authenticated()

                        // ==============================
                        // ORDERS
                        // ==============================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/orders/place/**"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/orders/user/**"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/orders"
                        ).hasAnyRole(
                                "ADMIN",
                                "RESTAURANT"
                        )

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/orders/*"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "ADMIN",
                                "RESTAURANT"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/orders/*/status"
                        ).hasAnyRole(
                                "ADMIN",
                                "RESTAURANT"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/orders/*/cancel"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "ADMIN",
                                "RESTAURANT"
                        )

                        // ==============================
                        // OTHER APIs
                        // ==============================

                        .requestMatchers(
                                "/api/payments/**",
                                "/api/addresses/**",
                                "/api/reviews/**",
                                "/api/notifications/**",
                                "/api/tracking/**"
                        ).authenticated()

                        // ==============================
                        // EVERYTHING ELSE
                        // ==============================

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}