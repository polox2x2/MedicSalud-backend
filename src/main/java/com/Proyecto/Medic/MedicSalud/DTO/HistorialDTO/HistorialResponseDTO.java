package com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HistorialResponseDTO {


    private Long id;

    private LocalDateTime fechaRegistro;

    private String diagnostico;

    private String tratamiento;

    private String observaciones;

    private Boolean estado;

    private Long pacienteId;
    private String pacienteNombre;
    private Integer pacienteDni;

    private Long medicoId;
    private String medicoNombre;



}
