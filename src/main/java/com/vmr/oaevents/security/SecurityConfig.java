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
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                            config.setAllowedHeaders(List.of("*"));
                            return config;
                        }))
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests((authorize) -> authorize

                                // fotos
                                .requestMatchers("/uploads/**").permitAll()

                                .requestMatchers("/auth/login").permitAll()

                                // Comprador
                                .requestMatchers(HttpMethod.POST, "/comprador").permitAll()
                                .requestMatchers(HttpMethod.GET, "/comprador", "/comprador/page", "/comprador/buscar/page").hasRole("RECINTO")

                                // Empresa
                                .requestMatchers(HttpMethod.POST, "/empresa").permitAll()
                                .requestMatchers(HttpMethod.GET, "/empresa", "/empresa/page", "/empresa/activas/page", "/empresa/inactivas/page", "/empresa/buscar/page").hasRole("RECINTO")
                                .requestMatchers(HttpMethod.PATCH, "/empresa/*/activar", "/empresa/*/desactivar").hasRole("RECINTO")

                                // Entrada
                                .requestMatchers(HttpMethod.POST, "/entrada/comprar").permitAll()
                                .requestMatchers(HttpMethod.GET, "/entrada/zona-evento/*/localidades-libres").permitAll()

                                // Evento
                                .requestMatchers(HttpMethod.GET, "/evento/aceptados/page").permitAll()
                                .requestMatchers(HttpMethod.GET, "/evento/buscar/aceptados/fechas/page").permitAll()
                                .requestMatchers(HttpMethod.GET, "/evento/*").permitAll()
                                .requestMatchers(HttpMethod.GET, "/evento", "/evento/page", "/evento/pendientes/page", "/evento/buscar/titulo/page", "/evento/buscar/fechas/page").hasRole("RECINTO")

                                // Localidad
                                .requestMatchers(HttpMethod.GET, "/localidad", "/localidad/page").hasRole("RECINTO")
                                .requestMatchers(HttpMethod.GET, "/localidad/zona/*", "/localidad/*/libres", "/localidad/*").permitAll()

                                // Qr
                                .requestMatchers(HttpMethod.POST, "/qr/check").hasAnyRole("RECINTO", "VALIDADOR")
                                .requestMatchers("/qr", "/qr/**").hasRole("RECINTO")

                                // Recinto
                                .requestMatchers(HttpMethod.GET, "/recinto", "/recinto/*").permitAll()
                                .requestMatchers("/recinto", "/recinto/**").hasRole("RECINTO")

                                // Rol
                                .requestMatchers(HttpMethod.GET, "/rol", "/rol/*").permitAll()
                                .requestMatchers("/rol", "/rol/**").hasRole("RECINTO")

                                // TipoEvento
                                .requestMatchers(HttpMethod.GET, "/tipo-evento", "/tipo-evento/*").permitAll()
                                .requestMatchers("/tipo-evento", "/tipo-evento/**").hasRole("RECINTO")

                                // Usuario
                                .requestMatchers(HttpMethod.GET, "/usuario", "/usuario/*").hasRole("RECINTO")
                                .requestMatchers(HttpMethod.POST, "/usuario").hasRole("RECINTO")
                                .requestMatchers(HttpMethod.PUT, "/usuario/*").hasRole("RECINTO")
                                .requestMatchers(HttpMethod.DELETE, "/usuario/*").hasRole("RECINTO")

                                // Validador
                                .requestMatchers("/validador", "/validador/**").hasRole("RECINTO")

                                // Zona
                                .requestMatchers(HttpMethod.GET, "/zona/recinto/*", "/zona/*").permitAll()
                                .requestMatchers("/zona", "/zona/**").hasRole("RECINTO")

                                // ZonaEvento
                                .requestMatchers(HttpMethod.GET, "/zona-evento", "/zona-evento/page").hasRole("RECINTO")
                                .requestMatchers(HttpMethod.GET, "/zona-evento/evento/*", "/zona-evento/*").permitAll()

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
