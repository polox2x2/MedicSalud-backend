package com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {


    private String nombre;
    private String apellido;
    private Integer dni;
    private String email;

}
