package com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroUsuarioDTO {
    private  String nombre ;
    private  String apellido;
    private  Integer dni;
    private  String email;
    private  String clave;
    private  String telefono;
    private  String direccion;
    private boolean estado;
    private LocalDate fechaNacimiento;
    private String especialidad;
}
