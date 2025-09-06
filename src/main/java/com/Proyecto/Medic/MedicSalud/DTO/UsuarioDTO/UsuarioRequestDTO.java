package com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
public class UsuarioRequestDTO {


    private String nombre;
    private String apellido;
    private Integer dni;
    private String email;
    private String clave;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;
    private Set<String> roles;
}
