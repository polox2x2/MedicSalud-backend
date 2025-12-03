package com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioUpDateDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private byte[] fotoPerfil;
}
