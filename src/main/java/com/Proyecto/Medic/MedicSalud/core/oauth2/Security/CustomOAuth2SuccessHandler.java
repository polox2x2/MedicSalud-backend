package com.Proyecto.Medic.MedicSalud.core.oauth2.Security;


import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Repository.RolRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import com.Proyecto.Medic.MedicSalud.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {


    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final RolRepository rolRepository;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authentication)
            throws IOException, ServletException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("name");

        Rol rolUsuario = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("No existe ROLE_USER"));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolUsuario);


        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setEmail(email);
                    nuevo.setNombre(nombre);
                    nuevo.setRoles(roles);
                    nuevo.setEstado(true);
                    return usuarioRepository.save(nuevo);
                });

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getClave() == null ? "" : usuario.getClave())
                .authorities("ROLE_USER")
                .build();


        String token = jwtService.generateToken(userDetails);

// Redirigir al frontend con el token
        String frontendUrl = "http://localhost:4200/oauth2/redirect";
        String redirectUrl = frontendUrl + "?token=" +
                URLEncoder.encode(token, StandardCharsets.UTF_8);

        response.sendRedirect(redirectUrl);
    }






    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }
}
