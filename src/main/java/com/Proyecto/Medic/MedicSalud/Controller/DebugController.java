package com.Proyecto.Medic.MedicSalud.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        if (auth == null) {
            return Map.of("auth", "null");
        }
        return Map.of(
                "principal", auth.getPrincipal(),
                "name", auth.getName(),
                "authorities", auth.getAuthorities()
        );
    }
}
