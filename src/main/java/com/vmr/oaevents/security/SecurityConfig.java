package com.vmr.oaevents.security;

import com.vmr.oaevents.service.impl.CustomUsuarioDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUsuarioDetailsService customUsuarioDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                        .cors(cors -> cors.configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOriginPatterns(List.of("*"));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            config.setAllowedHeaders(List.of("*"));
                            return config;
                        }))
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests((authorize) -> authorize

                                // fotos
                                .requestMatchers("/uploads/**").permitAll()

                                // ayuntamiento controller
                                .requestMatchers(HttpMethod.GET, "/ayuntamiento").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/ayuntamiento").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/ayuntamiento/{id}").hasRole("ADMIN")
                                .requestMatchers("/ayuntamiento/{id}/**").hasAnyRole("AYUNTAMIENTO", "ADMIN")

                                // cementerio controller
                                .requestMatchers(HttpMethod.GET, "/cementerio/{id}/tarifa").authenticated()
                                .requestMatchers(HttpMethod.POST, "/cementerio/{id}/tarifa").authenticated()
                                .requestMatchers(HttpMethod.GET, "/cementerio/{id}/zona").permitAll()
                                .requestMatchers(HttpMethod.POST, "/cementerio/{id}/zona").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/cementerio/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/cementerio/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cementerio/**").hasRole("ADMIN")

                                // ciudadano controller
                                .requestMatchers(HttpMethod.POST, "/ciudadano").permitAll()
                                .requestMatchers(HttpMethod.GET, "/ciudadano").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/ciudadano/**").hasRole("ADMIN")
                                .requestMatchers("/ciudadano/{id}/**").authenticated()

                                // concesion controller
                                .requestMatchers(HttpMethod.GET, "/concesion").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/concesion/{id}").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/concesion/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/concesion/{id}").authenticated()

                                // difunto controller
                                .requestMatchers(HttpMethod.GET, "/difunto").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/difunto/**").hasRole("ADMIN")
                                .requestMatchers("/difunto/{id}/**").authenticated()

                                // parcela controller
                                .requestMatchers(HttpMethod.GET, "/parcela").permitAll()
                                .requestMatchers(HttpMethod.GET, "/parcela/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/parcela/{id}/ayuntamiento").permitAll()
                                .requestMatchers(HttpMethod.GET, "/parcela/{id}/zona").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/parcela/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/parcela/**").hasRole("ADMIN")
                                .requestMatchers("/parcela/{id}/**").authenticated()

                                // servicio controller
                                .requestMatchers(HttpMethod.GET, "/servicio/{id}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/servicio").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/servicio/{id}/realizado").hasAnyRole("ADMIN", "AYUNTAMIENTO")
                                .requestMatchers(HttpMethod.PUT, "/servicio/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/servicio/**").hasRole("ADMIN")

                                // tarifa controller
                                .requestMatchers(HttpMethod.GET, "/tarifa").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/tarifa/{id}").authenticated()
                                .requestMatchers("/tarifa/{id}/**").authenticated()

                                // tipo servicio controller
                                .requestMatchers(HttpMethod.GET, "/tipo-servicio/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/tipo-servicio").hasAnyRole("ADMIN", "AYUNTAMIENTO")
                                .requestMatchers(HttpMethod.PUT, "/tipo-servicio/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/tipo-servicio/**").hasRole("ADMIN")

                                // tipo zona controller
                                .requestMatchers(HttpMethod.GET, "/tipo-zona/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/tipo-zona").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/tipo-zona/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/tipo-zona/**").hasRole("ADMIN")

                                // usuario controller
                                .requestMatchers(HttpMethod.GET, "/usuario/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/usuario/**").authenticated()

                                // zona controller
                                .requestMatchers(HttpMethod.GET, "/zona/{id}/parcela").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zona/{id}/parcela/libre").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zona/{id}/cementerio").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zona/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zona").hasRole("ADMIN")
                                .requestMatchers("/zona/**").authenticated()

                                .requestMatchers("/auth/login").permitAll()
                                .anyRequest().authenticated()
                        )
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                        // token expirado seguramente
                        .exceptionHandling(exception -> exception
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"error\": \"No tienes permiso.\"}");
                                }))
                        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Para que el controllador de login pueda autenticar al usuario
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUsuarioDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

}
