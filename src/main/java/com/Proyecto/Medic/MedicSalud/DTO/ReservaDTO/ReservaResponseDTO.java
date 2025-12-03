package com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO;

import com.Proyecto.Medic.MedicSalud.Entity.EstadoCita;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@Schema(name = "ReservaResponseDTO")
public class ReservaResponseDTO {

        private Long id;

        private String nombrePaciente;
        private Integer pacienteDni;

        private String nombreMedico;
        private Integer medicoDni;

        private String nombreSede;

        private LocalDateTime fechaCreacion;
        private LocalDate fechaCita;
        private LocalTime horaCita;

        private EstadoCita estadoCita;
        private Boolean estado;

        public ReservaResponseDTO(
                        Long id,
                        String nombrePaciente,
                        Integer pacienteDni,
                        String nombreMedico,
                        Integer medicoDni,
                        String nombreSede,
                        LocalDateTime fechaCreacion,
                        LocalDate fechaCita,
                        LocalTime horaCita,
                        EstadoCita estadoCita,
                        Boolean estado) {
                this.id = id;
                this.nombrePaciente = nombrePaciente;
                this.pacienteDni = pacienteDni;
                this.nombreMedico = nombreMedico;
                this.medicoDni = medicoDni;
                this.nombreSede = nombreSede;
                this.fechaCreacion = fechaCreacion;
                this.fechaCita = fechaCita;
                this.horaCita = horaCita;
                this.estadoCita = estadoCita;
                this.estado = estado;
        }

        public ReservaResponseDTO() {
        }
}
