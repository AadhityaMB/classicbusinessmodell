package com.classicbusinessmodel_schema.backend.config;

import org.springframework.http.HttpMethod;
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
                                .cors(Customizer.withDefaults())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/customers/{customerNumber}/orders").hasAnyRole("ORDERS", "CUSTOMER")
                                                .requestMatchers("/api/customers/**").hasRole("CUSTOMER")
                                                .requestMatchers("/api/payments/**").hasRole("PAYMENT")
                                                .requestMatchers("/api/employees/**").hasRole("EMPLOYEE")
                                                .requestMatchers("/api/offices/**").hasRole("OFFICE")
                                                .requestMatchers("/api/orders/*/items/**").hasRole("ORDERDETAILS")
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
                                .password("priya@123")
                                .roles("CUSTOMER", "PAYMENT")
                                .build();

                UserDetails employeeUser = User.withDefaultPasswordEncoder()
                                .username("harini")
                                .password("harini@123")
                                .roles("EMPLOYEE", "OFFICE")
                                .build();

                UserDetails ordersUser = User.withDefaultPasswordEncoder()
                                .username("pritika")
                                .password("pritika@123")
                                .roles("ORDERS")
                                .build();

                UserDetails productUser = User.withDefaultPasswordEncoder()
                                .username("meena")
                                .password("meena@123")
                                .roles("PRODUCT", "PRODUCTLINE","ORDERDETAILS")
                                .build();

                UserDetails reportUser = User.withDefaultPasswordEncoder()
                                .username("aadhi")
                                .password("aadhi@123")
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
