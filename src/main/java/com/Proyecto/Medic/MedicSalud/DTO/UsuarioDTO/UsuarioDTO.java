package com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Set<String> roles;
    private boolean estado;
}
