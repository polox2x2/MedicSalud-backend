package com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroUsuarioDTO {
    String nombre ;
    String apellido;
    Integer dni;
    String email;
    String clave;
    String telefono;
    String direccion;
    LocalDateTime fechanaciemiento;
}
