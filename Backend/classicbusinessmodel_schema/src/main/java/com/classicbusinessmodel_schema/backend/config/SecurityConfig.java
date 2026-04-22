package com.classicbusinessmodel_schema.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .cors(Customizer.withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/customers/**").hasRole("CUSTOMER")
                                                .requestMatchers("/api/payments/**").hasRole("PAYMENT")
                                                .requestMatchers("/api/employees/**").hasRole("EMPLOYEE")
                                                .requestMatchers("/api/offices/**").hasRole("OFFICE")
                                                .requestMatchers("/api/orders/**").hasRole("ORDERS")
                                                .requestMatchers("/api/products/**").hasRole("PRODUCT")
                                                .requestMatchers("/api/product-lines/**").hasRole("PRODUCTLINE")
                                                .requestMatchers("/api/reports/**").hasRole("REPORT")
                                                .anyRequest().authenticated())
                                .httpBasic(Customizer.withDefaults());

                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {

                UserDetails customerUser = User.withDefaultPasswordEncoder()
                                .username("priya")
                                .password("1234")
                                .roles("CUSTOMER", "PAYMENT")
                                .build();

                UserDetails employeeUser = User.withDefaultPasswordEncoder()
                                .username("harini")
                                .password("1234")
                                .roles("EMPLOYEE", "OFFICE")
                                .build();

                UserDetails ordersUser = User.withDefaultPasswordEncoder()
                                .username("pritika")
                                .password("1234")
                                .roles("ORDERS")
                                .build();

                UserDetails productUser = User.withDefaultPasswordEncoder()
                                .username("meena")
                                .password("1234")
                                .roles("PRODUCT", "PRODUCTLINE")
                                .build();

                UserDetails reportUser = User.withDefaultPasswordEncoder()
                                .username("aadhi")
                                .password("1234")
                                .roles("REPORT")
                                .build();

                return new InMemoryUserDetailsManager(
                                customerUser,
                                employeeUser,
                                ordersUser,
                                productUser,
                                reportUser);
        }
}