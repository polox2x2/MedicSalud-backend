package com.Proyecto.Medic.MedicSalud.Security;

;
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


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();

    }


    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService uds,
                                                         PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationProvider authenticationProvider) throws Exception {

        http
                .cors(c -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(auth -> auth

                        // 1) Permitir TODOS los preflight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2) Endpoints de prueba PayPal
                        .requestMatchers("/api/paypal-test/**").permitAll()

                        .requestMatchers("/oauth2/**", "/login/**", "/error").permitAll()

                        //  ENDPOINTS PÚBLICOS
                        .requestMatchers(HttpMethod.POST,
                                "/api/auth/login",
                                "/api/auth/register"
                        ).permitAll()
                        .requestMatchers(
                                "/actuator/health",
                                "/api/mail/envio"
                        ).permitAll()
                        .requestMatchers("/api/usuarios/crear-paciente").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reservas/lista/**").permitAll()
                        .requestMatchers("/api/recetas/**").permitAll()

                        // DEBUG
                        .requestMatchers("/api/debug/**").authenticated()

                        // CUALQUIER USUARIO AUTENTICADO
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()

                        // SOLO ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/ventas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/medicamentos/**").hasRole("ADMIN")

                        // SOLO PACIENTE
                        .requestMatchers("/api/mis-datos/**").hasRole("PACIENTE")

                        // ADMIN + MEDICO
                        .requestMatchers(HttpMethod.GET, "/api/pacientes/**")
                        .hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.POST, "/api/pacientes/**")
                        .hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.POST,"/api/ventas").hasAnyRole("ADMIN","MEDICO")

                        // reservas por paciente
                        .requestMatchers(HttpMethod.GET, "/api/reservas/**")
                        .hasAnyRole("PACIENTE", "ADMIN", "MEDICO")

                        // creación de reserva del propio paciente
                        .requestMatchers(HttpMethod.POST, "/api/reservas/paciente/**")
                        .hasRole("PACIENTE")

                        // horarios gestionados por admin/medico
                        .requestMatchers(HttpMethod.POST, "/api/horarios/**")
                        .hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/horarios/mis-horarios")
                        .hasAnyRole("ADMIN", "MEDICO")

                        // visible para todos los roles
                        .requestMatchers(HttpMethod.GET, "/api/horarios/medico/**")
                        .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                        // CUALQUIER OTRO ENDPOINT
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200","http://localhost:5500", "http://127.0.0.1:5500"));
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(false);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
