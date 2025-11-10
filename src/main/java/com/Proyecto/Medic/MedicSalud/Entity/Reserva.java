package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // Fecha en la que se creó la reserva
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Día de la cita
    @NotNull
    @Future
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;

    // Hora exacta de la cita
    @NotNull
    @Column(name = "hora_cita", nullable = false)
    private LocalTime horaCita;

    @Column(nullable = false)
    private Boolean estadoCita = true;

    // Relación con médico
    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    // Relación con sede
    @ManyToOne
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    // Relación con paciente
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    // Control de concurrencia (evita doble confirmación)
    @Version
    private Long version;
}
