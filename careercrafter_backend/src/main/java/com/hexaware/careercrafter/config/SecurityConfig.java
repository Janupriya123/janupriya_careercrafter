package com.hexaware.careercrafter.config;
import com.hexaware.careercrafter.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.Customizer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtFilter;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
               "http://18.61.69.8",
                "http://localhost:3000"
        ));

        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth

                                .requestMatchers(
                                        "/user/login",
                                        "/user/create",
                                        "/user/forgot-password",
                                        "/user/verify-otp",
                                        "/user/reset-password"
                                ).permitAll()
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**")
                                .permitAll()
                                .requestMatchers(
                                        "/job/add",
                                        "/job/update/**",
                                        "/job/delete/**",
                                        "/employer/**")
                                .hasRole("EMPLOYER")

                                .requestMatchers("/application/**")
                                .hasAnyRole("JOBSEEKER","EMPLOYER")

                                .requestMatchers("/profile/**")
                                .hasRole("JOBSEEKER")

                                .requestMatchers("/resume/upload")
                                .hasRole("JOBSEEKER")

                                .requestMatchers("/resume/update/**")
                                .hasRole("JOBSEEKER")

                                .requestMatchers("/resume/delete/**")
                                .hasRole("JOBSEEKER")

                                .requestMatchers("/resume/profile/**")
                                .hasRole("JOBSEEKER")

                                .requestMatchers("/resume/download/**")
                                .hasAnyRole("JOBSEEKER","EMPLOYER")
                                .requestMatchers(
                                        "/admin/**")
                                .hasRole("ADMIN")

                                .anyRequest()
                                .authenticated()


                  /*  .requestMatchers(
                                "/user/login",
                                "/user/register")
                        .permitAll()

                        .requestMatchers("/job/**")
                        .permitAll()

                        .requestMatchers("/profile/**")
                        .permitAll()

                        .requestMatchers("/resume/**")
                        .permitAll()

                        .requestMatchers("/user/**")
                        .permitAll()

                        .requestMatchers("/application/**")
                        .permitAll()

                        .requestMatchers("/employer/**")
                        .permitAll()

                        .anyRequest()
                        .authenticated() */

                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
