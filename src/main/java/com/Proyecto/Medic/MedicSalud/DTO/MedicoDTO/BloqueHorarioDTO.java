package com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloqueHorarioDTO {
    private String hora;
    private boolean disponible;
    private String estado;
}