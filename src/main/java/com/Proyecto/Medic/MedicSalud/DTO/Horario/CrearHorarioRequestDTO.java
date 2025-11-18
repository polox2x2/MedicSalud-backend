package com.Proyecto.Medic.MedicSalud.DTO.Horario;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearHorarioRequestDTO {

    @NotNull(message = "El día es obligatorio")
    private DayOfWeek dia;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora fin es obligatoria")
    private LocalTime horaFin;


}
