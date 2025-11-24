package com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Schema(name = "ReservaCrearDTO", description = "Datos necesarios para crear una reserva")
public class CrearReservaDTO {


    private Long medicoId;
    private Integer medicoDni;


    private Integer pacienteDni;


    private Long sedeId;
    private String sedeNombre;
    private String sedeDireccion;

    @NotNull
    private LocalDate fechaCita;
    @NotNull
    private LocalTime horaCita;

    private Boolean estado;
}