package com.Proyecto.Medic.MedicSalud.Service;


import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import com.Proyecto.Medic.MedicSalud.Entity.Sede;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.ReservaRepository;
import com.Proyecto.Medic.MedicSalud.Repository.SedeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final SedeRepository sedeRepository;


@Transactional
public Reserva crearReserva (Long pacienteId, Long medicoId, Long sedeId, LocalDate fechaCita, LocalTime horaCita) {
    // 1)
    if (fechaCita == null || horaCita == null) {
        throw new IllegalArgumentException("fechaCita y horaCita son obligatorias");
    }

    // 2) Evitar doble booking (cuenta solo reservas activas)
    boolean ocupado = reservaRepository.existsByMedicoIdAndFechaCitaAndHoraCitaAndEstadoCitaTrue(
            medicoId, fechaCita, horaCita
    );
    if (ocupado) {
        throw new IllegalStateException("El médico ya tiene una reserva en esa fecha y hora");
    }

    // 3) Cargar referencias
    Paciente paciente = pacienteRepository.getReferenceById(pacienteId);
    Medico medico   = medicoRepository.getReferenceById(medicoId);
    Sede sede     = sedeRepository.getReferenceById(sedeId);

    // 4) Crear y guardar
    Reserva r = new Reserva();
    r.setPaciente(paciente);
    r.setMedico(medico);
    r.setSede(sede);
    r.setFechaCita(fechaCita);
    r.setHoraCita(horaCita);
    r.setEstadoCita(true);
    r.setFechaCreacion(LocalDateTime.now());

    return reservaRepository.save(r);
}
    @Transactional(readOnly = true)
    public java.util.List<Reserva> reservasDePaciente(Long pacienteId) {
        return reservaRepository.findByPacienteIdOrderByFechaCitaDesc(pacienteId);
    }

    @Transactional
    public Reserva cancelar(Long reservaId) {
        var r = reservaRepository.findById(reservaId).orElseThrow();
        r.setEstadoCita(false);
        return r;
    }
}
