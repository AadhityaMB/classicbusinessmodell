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

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/customers/**").hasRole("CUSTOMER")
                                                .requestMatchers("/api/payment/**").hasRole("PAYMENT")
                                                .requestMatchers("/api/employees/**").hasRole("EMPLOYEE")
                                                .requestMatchers("/api/office/**").hasRole("OFFICE")
                                                .requestMatchers("/api/orders/**").hasRole("ORDERS")
                                                .requestMatchers("/api/products/**").hasRole("PRODUCT")
                                                .requestMatchers("/api/product-lines/**").hasRole("PRODUCTLINE")
                                                .requestMatchers("/api/reports/**").hasRole("REPORT")
                                                .anyRequest().authenticated())
                                .formLogin(Customizer.withDefaults())
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