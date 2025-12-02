package com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CrearReservaPacienteDTO {


    @NotNull
    private Integer medicoDni;     // médico elegido

    @NotNull
    private Long sedeId;           // sede elegida

    @NotNull
    private LocalDate fechaCita;

    @NotNull
    private LocalTime horaCita;
}
