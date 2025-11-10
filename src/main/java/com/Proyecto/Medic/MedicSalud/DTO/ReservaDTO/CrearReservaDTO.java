package com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Schema(name = "ReservaCrearDTO", description = "Datos necesarios para crear una reserva")
public class CrearReservaDTO {


    private Long pacienteId;
    private Integer pacienteDni;
    private String pacienteNombreUsuario;


    private Long medicoId;
    private Integer medicoDni;


    private Long sedeId;
    private String sedeNombre;
    private String sedeDireccion;


    @NotNull
    private LocalDate fechaCita;
    @NotNull
    private LocalTime horaCita;
}
