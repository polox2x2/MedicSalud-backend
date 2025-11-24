package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "reservas",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_reserva_medico_fecha_hora",
                columnNames = {"id_medico", "fecha_cita", "hora_cita"}
        )
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @NotNull
    @Future(message = "La fecha de la cita debe ser futura")
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;

    @NotNull
    @Column(name = "hora_cita", nullable = false)
    private LocalTime horaCita;

    @NotNull
    @Column(nullable = false, name = "estado_cita")
    private Boolean estadoCita = true;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_medico", nullable = false)
    @NotNull(message = "El médico es obligatorio")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private Medico medico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sede", nullable = false)
    @NotNull(message = "La sede es obligatoria")
    private Sede sede;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    @NotNull(message = "El paciente es obligatorio")
    private Paciente paciente;

    @Version
    private Long version;

    @AssertTrue(message = "La hora de la cita debe estar dentro del horario del médico")
    public boolean isHoraDentroHorarioMedico() {
        if (medico == null || fechaCita == null || horaCita == null) return true;

        DayOfWeek dia = fechaCita.getDayOfWeek();

        if (medico.getHorarios() == null || medico.getHorarios().isEmpty()) return false;

        return medico.getHorarios().stream()
                .filter(h -> h.getDia() == dia)
                .anyMatch(h ->
                        !horaCita.isBefore(h.getHoraInicio()) &&
                                !horaCita.isAfter(h.getHoraFin())
                );
    }
}
