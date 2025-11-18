package com.Proyecto.Medic.MedicSalud.Service;


import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.*;
import com.Proyecto.Medic.MedicSalud.Mappers.ReservaMapper;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.ReservaRepository;
import com.Proyecto.Medic.MedicSalud.Repository.SedeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final SedeRepository sedeRepository;

    @Transactional
    public Reserva crearReserva (CrearReservaDTO req) {
        if (req.getFechaCita() == null || req.getHoraCita() == null) {
            throw new IllegalArgumentException("fecha de la Cita y hora de la Cita son obligatorias");
        }

        Paciente paciente = resolvePaciente(req);
        Medico   medico   = resolveMedico(req);
        Sede     sede     = resolveSede(req);

        boolean ocupado = reservaRepository
                .existsByMedicoIdAndFechaCitaAndHoraCitaAndEstadoCitaTrue(
                        medico.getId(), req.getFechaCita(), req.getHoraCita());

        if (ocupado) {
            throw new IllegalStateException("El médico ya tiene una reserva en esa fecha y hora");
        }

        Reserva r = new Reserva();
        r.setPaciente(paciente);
        r.setMedico(medico);
        r.setSede(sede);
        r.setFechaCita(req.getFechaCita());
        r.setHoraCita(req.getHoraCita());
        r.setEstadoCita(true);
        r.setFechaCreacion(LocalDateTime.now());

        return reservaRepository.save(r);
    }

    private Paciente resolvePaciente(CrearReservaDTO req) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return pacienteRepository.findByUsuario_Email(username)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
    }

    private Medico resolveMedico(CrearReservaDTO req) {
        if (req.getMedicoId() != null)
            return medicoRepository.getReferenceById(req.getMedicoId());

        if (req.getMedicoDni() != null)
            return medicoRepository.findByDni(req.getMedicoDni())
                    .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado por DNI"));

        throw new IllegalArgumentException("Debe enviar medicoId o medicoDni");
    }

    private Sede resolveSede(CrearReservaDTO req) {
        if (req.getSedeId() != null)
            return sedeRepository.getReferenceById(req.getSedeId());

        if (req.getSedeNombre() != null && !req.getSedeNombre().isBlank())
            return sedeRepository.findByNombreClinicaIgnoreCase(req.getSedeNombre().trim())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada por nombre"));

        if (req.getSedeDireccion() != null && !req.getSedeDireccion().isBlank())
            return sedeRepository.findByDireccionIgnoreCase(req.getSedeDireccion().trim())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada por dirección"));

        throw new IllegalArgumentException("Debe enviar sedeId o sedeNombre o sedeDireccion");
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarReservasPacienteLogueado() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Paciente paciente = pacienteRepository.findByUsuario_Email(username)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        return reservaRepository.findByPacienteIdOrderByFechaCitaDesc(paciente.getId())
                .stream()
                .map(ReservaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Reserva> reservasDePaciente(Long pacienteId) {
        return reservaRepository.findByPacienteIdOrderByFechaCitaDesc(pacienteId);
    }

    @Transactional
    public Reserva cancelar(Long reservaId) {
        var r = reservaRepository.findById(reservaId).orElseThrow();
        r.setEstadoCita(false);
        return r;
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarActivos() {
        return reservaRepository.findByEstadoCitaTrue()
                .stream()
                .map(ReservaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminarLogico(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("reservacion no encontrado"));
        reserva.setEstadoCita(false);
        reservaRepository.save(reserva);
    }
}

