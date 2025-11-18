package com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisponibilidadMedicoDTO {

    private Long medicoId;
    private String nombreMedico;
    private String especialidad;
    private String nombreSede;

    private LocalDate fecha;
    private List<BloqueHorarioDTO> horarios;
}
