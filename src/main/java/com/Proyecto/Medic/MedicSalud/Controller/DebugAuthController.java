package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class DebugAuthController {
    private final UsuarioRepository repo;
    private final PasswordEncoder enc;

    @GetMapping("/debug-check")
    public String check(@RequestParam String email, @RequestParam String raw) {
        var u = repo.findByEmail(email).orElse(null);
        if (u == null) return "NO_USER";
        return enc.matches(raw, u.getClave()) ? "MATCH" : "NO_MATCH";
    }
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}