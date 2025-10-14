package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.Auth.JwtResponseDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Auth.LoginRequestDTO;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import com.Proyecto.Medic.MedicSalud.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Value("${app.jwt.expiration-ms}")
    private long appExpirationMs;   // <-- agrega esto

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO req) {
        var auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        var user  = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        var token = jwtService.generateToken(user);
        var exp   = Instant.now().plusMillis(appExpirationMs);

        var roles = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponseDTO(token, "Bearer", exp, user.getUsername(), roles));
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build(); // 204
    }

    @GetMapping("/me")
    public Map<String,Object> me(Authentication  auth) {
        return Map.of(
                "username", auth.getName(),
                "authorities", auth.getAuthorities()
        );
    }
}