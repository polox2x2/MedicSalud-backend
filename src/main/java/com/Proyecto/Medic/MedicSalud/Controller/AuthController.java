package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.Auth.JwtResponseDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Auth.LoginRequestDTO;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import com.Proyecto.Medic.MedicSalud.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Value("${app.jwt.expiration-ms}")
    private long appExpirationMs;   // <-- agrega esto

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO req) {
        try {
            var auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            var user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            var token = jwtService.generateToken(user);
            var exp = Instant.now().plusMillis(appExpirationMs);

            var roles = user.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toSet());

            return ResponseEntity.ok(new JwtResponseDTO(token, "Bearer", exp, user.getUsername(), roles));
        } catch (org.springframework.security.core.AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Credenciales inválidas"));
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build(); // 204
    }

    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        var roles = auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());
        return ResponseEntity.ok(Map.of("username", auth.getName(), "roles", roles));
    }






}