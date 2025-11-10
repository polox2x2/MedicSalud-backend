package com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Builder
@Schema(name = "ReservaResponseDTO")
@Data
public class ReservaResponseDTO {

        private Long id;

        private String nombrePaciente;
        private String nombreMedico;
        private String nombreSede;

        private LocalDateTime fechaCreacion;
        private LocalDate fechaCita;
        private LocalTime horaCita;

        private Boolean estadoCita;
}
