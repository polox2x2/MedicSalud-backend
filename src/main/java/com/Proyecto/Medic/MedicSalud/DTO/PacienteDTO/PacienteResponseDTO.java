package com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteResponseDTO {


    private Long id;
    private String nombreUsuario;
    private Integer dni;
    private String email;
    private String rol;
    private String telefono;
    private Boolean estado;



}
