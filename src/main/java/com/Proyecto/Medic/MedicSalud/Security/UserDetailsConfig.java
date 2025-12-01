package com.Proyecto.Medic.MedicSalud.Security;

import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

    private final UsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return (String usernameOrEmail) -> {
            Usuario u = usuarioRepository.findByEmail(usernameOrEmail)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Usuario no encontrado: " + usernameOrEmail));

            var authorities = u.getRoles().stream()
                    .filter(r -> r != null && r.getNombre() != null)
                    .map(r -> r.getNombre().trim().toUpperCase())
                    .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            boolean enabled = u.getEstado() == null || Boolean.TRUE.equals(u.getEstado());

            return User.builder()
                    .username(u.getEmail())
                    .password(u.getClave())
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(!enabled)
                    .build();
        };
    }
}
