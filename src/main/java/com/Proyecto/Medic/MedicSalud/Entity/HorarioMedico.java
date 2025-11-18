package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "horarios_medicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HorarioMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El día es obligatorio")
    private DayOfWeek dia;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull(message = "Debe pertenecer a un médico")
    private Medico medico;


    @AssertTrue(message = "La hora fin debe ser mayor que la hora inicio")
    public boolean isIntervaloValido() {
        if (horaInicio == null || horaFin == null) return true;
        return horaFin.isAfter(horaInicio);
    }

}
