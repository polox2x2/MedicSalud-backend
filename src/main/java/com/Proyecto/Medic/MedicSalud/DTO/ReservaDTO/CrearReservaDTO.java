package com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Schema(name = "ReservaCrearDTO", description = "Datos necesarios para crear una reserva")
public class CrearReservaDTO {

    @NotNull
    private Long pacienteId;

    @NotNull
    private Long medicoId;

    @NotNull
    private Long sedeId;

    @NotNull
    private LocalDate fechaCita;

    @NotNull
    private LocalTime horaCita;
}
