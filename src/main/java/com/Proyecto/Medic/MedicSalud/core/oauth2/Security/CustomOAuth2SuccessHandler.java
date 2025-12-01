package com.Proyecto.Medic.MedicSalud.core.oauth2.Security;

import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.RolRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import com.Proyecto.Medic.MedicSalud.Service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PacienteRepository pacienteRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String givenName = oAuth2User.getAttribute("given_name");
        String familyName = oAuth2User.getAttribute("family_name");
        String fullName = oAuth2User.getAttribute("name");

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "No se pudo obtener el email desde Google");
            return;
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setEmail(email);
                    nuevo.setNombre(givenName != null ? givenName :
                            (fullName != null ? fullName : "Paciente"));
                    nuevo.setApellido(familyName != null ? familyName : "");
                    nuevo.setDni(null);

                    // Contraseña aleatoria y cifrada SIN inyectar el bean
                    String randomPassword = UUID.randomUUID().toString();
                    String encoded = new BCryptPasswordEncoder().encode(randomPassword);
                    nuevo.setClave(encoded);

                    Rol rolPaciente = rolRepository.findByNombre("PACIENTE")
                            .orElseThrow(() -> new RuntimeException("Falta crear el rol PACIENTE"));

                    Set<Rol> roles = new HashSet<>();
                    roles.add(rolPaciente);
                    nuevo.setRoles(roles);
                    nuevo.setEstado(true);

                    Usuario guardado = usuarioRepository.save(nuevo);

                    Paciente paciente = new Paciente();
                    paciente.setNombreUsuario(guardado.getNombre() + guardado.getApellido());
                    paciente.setDni(guardado.getDni());
                    paciente.setUsuario(guardado);

                    pacienteRepository.save(paciente);

                    return guardado;
                });

        String[] authorities = usuario.getRoles()
                .stream()
                .map(Rol::getNombre)
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .toArray(String[]::new);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getClave() == null ? "" : usuario.getClave())
                .authorities(authorities)
                .build();

        String token = jwtService.generateToken(userDetails);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
    }
}
