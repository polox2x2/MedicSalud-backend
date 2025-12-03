package com.Proyecto.Medic.MedicSalud.Security;

import com.Proyecto.Medic.MedicSalud.core.oauth2.Security.CustomOAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtFilter;
        private final CustomOAuth2SuccessHandler oAuth2SuccessHandler;
        private final UserDetailsService userDetailsService; // viene de UserDetailsConfig

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
                return cfg.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .cors(c -> {
                                })
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                                .exceptionHandling(e -> e.authenticationEntryPoint(
                                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                                .authorizeHttpRequests(auth -> auth

                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                                .requestMatchers("/api/paypal-test/**").permitAll()

                                                .requestMatchers("/oauth2/**", "/login/**",
                                                                "/error", "/api-docs/**", "/v3/api-docs/**")
                                                .permitAll()

                                                .requestMatchers(HttpMethod.POST,
                                                                "/api/auth/login",
                                                                "/api/auth/register")
                                                .permitAll()

                                                .requestMatchers(
                                                                "/actuator/health",
                                                                "/api/mail/envio")
                                                .permitAll()

                                                .requestMatchers("/api/usuarios/crear-paciente").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/reservas/lista/**").permitAll()
                                                .requestMatchers("/api/recetas/**").permitAll()

                                                .requestMatchers("/api/debug/**").authenticated()

                                                .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()

                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/api/ventas/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/api/medicamentos/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers("/api/mis-datos/**").hasRole("PACIENTE")

                                                .requestMatchers(HttpMethod.POST, "/api/historiales/**")
                                                .hasRole("MEDICO")
                                                .requestMatchers(HttpMethod.PUT, "/api/historiales/**")
                                                .hasRole("MEDICO")
                                                .requestMatchers(HttpMethod.DELETE, "/api/historiales/**")
                                                .hasRole("MEDICO")

                                                .requestMatchers(HttpMethod.GET, "/api/historiales/**")
                                                .hasAnyRole("MEDICO", "ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/api/pacientes/me")
                                                .hasAnyRole("PACIENTE", "ADMIN", "MEDICO")
                                                .requestMatchers(HttpMethod.GET, "/api/pacientes/**")
                                                .hasAnyRole("ADMIN", "MEDICO")
                                                .requestMatchers(HttpMethod.POST, "/api/pacientes/**")
                                                .hasAnyRole("ADMIN", "MEDICO")
                                                .requestMatchers(HttpMethod.POST, "/api/ventas")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.GET, "/api/reservas/**")
                                                .hasAnyRole("PACIENTE", "ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.POST, "/api/reservas/paciente/**")
                                                .hasRole("PACIENTE")

                                                .requestMatchers(HttpMethod.POST, "/api/horarios/**")
                                                .hasAnyRole("ADMIN", "MEDICO")
                                                .requestMatchers(HttpMethod.GET, "/api/horarios/mis-horarios")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.GET, "/api/horarios/medico/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .loginPage("/oauth2/authorization/google")
                                                .successHandler(oAuth2SuccessHandler))
                                .formLogin(form -> form.disable())
                                .logout(logout -> logout.disable())
                                .authenticationProvider(authenticationProvider())
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of(
                                "http://localhost:4200",
                                "http://localhost:5500",
                                "http://127.0.0.1:5500",
                                "https://medicsalud-clinic.web.app/",
                                "https://medicsalud-clinic.web.app/"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                config.setExposedHeaders(List.of("Authorization"));
                config.setAllowCredentials(false);

                var source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }
}
