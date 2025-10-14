package com.Proyecto.Medic.MedicSalud.DTO.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDTO {
    private String token;
    private String type;
    private Instant fechaExpiracion;// fecha de expiración
    private String username;
    private Set<String> roles;


}
