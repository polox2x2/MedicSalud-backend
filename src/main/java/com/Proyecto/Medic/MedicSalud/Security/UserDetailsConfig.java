package com.Proyecto.Medic.MedicSalud.Security;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User; // builder de Spring
import org.springframework.security.core.userdetails.UserDetailsService; // interfaz de Spring
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

    private final UsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return (String usernameOrEmail) -> {
            // aquí usamos el email como "username"
            Usuario u = usuarioRepository.findByEmail(usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + usernameOrEmail));

            var authorities = u.getRoles().stream()
                    .filter(r -> r != null && r.getNombre() != null)
                    .map(r -> "ROL_" + r.getNombre().trim().toUpperCase())
                    .map(SimpleGrantedAuthority::new) // <- requiere import correcto
                    .collect(Collectors.toSet());

            boolean enabled = u.getEstado() == null || Boolean.TRUE.equals(u.getEstado());

            return User.builder()
                    .username(u.getEmail())    // principal = email
                    .password(u.getClave())    // hash BCrypt
                    .authorities(authorities)  // colección de GrantedAuthority
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(!enabled)
                    .build();
        };
    }
}