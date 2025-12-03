package com.Proyecto.Medic.MedicSalud.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "medico" })
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @NotNull
    @FutureOrPresent(message = "La fecha de la cita debe ser hoy o futura")
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;

    @NotNull
    @Column(name = "hora_cita", nullable = false)
    private LocalTime horaCita;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "estado_cita")
    private EstadoCita estadoCita = EstadoCita.PENDIENTE;

    @Builder.Default
    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_medico", nullable = false)
    @NotNull(message = "El médico es obligatorio")
    @JsonIgnore
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_horario_medico")
    private HorarioMedico horarioMedico;

    @Version
    private Long version;

    @AssertTrue(message = "La hora de la cita debe estar dentro del horario del médico")
    public boolean isHoraDentroHorarioMedico() {
        if (medico == null || fechaCita == null || horaCita == null) {
            return true;
        }

        if (!Hibernate.isInitialized(medico.getHorarios())) {
            return true;
        }

        if (medico.getHorarios() == null || medico.getHorarios().isEmpty()) {
            return false;
        }

        return medico.getHorarios().stream()
                .filter(h -> h.getFecha().equals(fechaCita))
                .anyMatch(h -> !horaCita.isBefore(h.getHoraInicio()) &&
                        !horaCita.isAfter(h.getHoraFin()));
    }
}
