package com.Proyecto.Medic.MedicSalud.DTO.Horario;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearHorarioRequestDTO {

    @NotNull(message = "La fecha es obligatoria")
    private java.time.LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora fin es obligatoria")
    private LocalTime horaFin;

}
