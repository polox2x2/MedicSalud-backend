package com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RecetaResponseDTO {

    private Long pacienteId;
    private String pacienteNombre;

    private Long medicoId;
    private String medicoNombre;

    private Long medicamentoId;
    private String medicamentoNombre;

    private String dosis;
    private String indicaciones;
    private LocalDateTime fechaCreacion;
}
