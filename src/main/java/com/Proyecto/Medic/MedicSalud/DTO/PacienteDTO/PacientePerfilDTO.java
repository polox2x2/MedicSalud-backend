package com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacientePerfilDTO {

    private Long id;
    private String nombreUsuario; // Nombre de usuario en Paciente (display name)
    private Integer dni;
    private String nombre; // Nombre real del Usuario
    private String apellido; // Apellido real del Usuario
    private String email;
    private String telefono;
    private String direccion;
    private byte[] fotoPerfil;
    private String rol;
    private Boolean estado;
}
