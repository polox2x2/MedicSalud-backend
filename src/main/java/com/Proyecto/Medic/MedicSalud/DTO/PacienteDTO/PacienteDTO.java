package com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {

    private Long id;
    private String nombreUsuario;
    private Integer dni;
    private String rol;
    private Boolean estado;

}
