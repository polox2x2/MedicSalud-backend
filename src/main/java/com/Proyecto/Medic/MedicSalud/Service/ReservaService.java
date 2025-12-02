package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaPacienteDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import com.Proyecto.Medic.MedicSalud.Entity.Sede;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.ReservaRepository;
import com.Proyecto.Medic.MedicSalud.Repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final SedeRepository sedeRepository;

    @Transactional
    public ReservaResponseDTO  crearReservaPaciente(CrearReservaPacienteDTO req) {

        Paciente paciente = resolvePacienteFromToken();


        Medico medico = medicoRepository.buscarPorDniActivo(req.getMedicoDni())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Médico no encontrado con DNI: " + req.getMedicoDni()
                ));

        Sede sede = sedeRepository.findById(req.getSedeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sede no encontrada con id: " + req.getSedeId()
                ));


        boolean ocupado = reservaRepository
                .existsByMedico_IdAndFechaCitaAndHoraCitaAndEstadoCitaTrue(
                        medico.getId(),
                        req.getFechaCita(),
                        req.getHoraCita()
                );

        if (ocupado) {
            throw new IllegalStateException("El médico ya tiene una cita en esa fecha y hora");
        }

        // Crear reserva
        Reserva r = new Reserva();
        r.setPaciente(paciente);
        r.setMedico(medico);
        r.setSede(sede);
        r.setFechaCita(req.getFechaCita());
        r.setHoraCita(req.getHoraCita());
        r.setEstadoCita(true);
        r.setFechaCreacion(LocalDateTime.now());

        Reserva reserva = reservaRepository.save(r);

        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getPaciente().getNombreUsuario(),
                reserva.getPaciente().getDni(),
                (reserva.getMedico() != null && reserva.getMedico().getUsuario() != null)
                        ? reserva.getMedico().getUsuario().getNombre()
                        : "Médico eliminado",
                reserva.getMedico() != null ? reserva.getMedico().getDni() : null,
                reserva.getSede() != null ? reserva.getSede().getNombreClinica() : null,
                reserva.getFechaCreacion(),
                reserva.getFechaCita(),
                reserva.getHoraCita(),
                reserva.getEstadoCita()
        );
    }

    // Helper para paciente desde el token (usado solo por crearReservaPaciente)
    private Paciente resolvePacienteFromToken() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return pacienteRepository.findByUsuario_Email(email)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Paciente no encontrado (token): " + email
                ));


    }


    @Transactional
    public Reserva crearReserva(CrearReservaDTO req) {

        Paciente paciente = resolvePaciente(req);
        Medico medico = resolveMedico(req);
        Sede sede = resolveSede(req);

        boolean ocupado = reservaRepository
                .existsByMedico_IdAndFechaCitaAndHoraCitaAndEstadoCitaTrue(
                        medico.getId(),
                        req.getFechaCita(),
                        req.getHoraCita()
                );

        if (ocupado) {
            throw new IllegalStateException("El médico ya tiene una cita en esa fecha y hora");
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

    /** PACIENTE — por DNI o token */
    private Paciente resolvePaciente(CrearReservaDTO req) {
        // 1) DNI del paciente
        if (req.getPacienteDni() != null) {
            return pacienteRepository.buscarPorDni(req.getPacienteDni())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Paciente no encontrado con DNI: " + req.getPacienteDni()
                    ));
        }

        // 2) Token del paciente
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return pacienteRepository.findByUsuario_Email(email)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado (token)"));
    }

    /** MEDICO — por ID, por DNI o token */
    private Medico resolveMedico(CrearReservaDTO req) {

        if (req.getMedicoId() != null) {
            return medicoRepository.findById(req.getMedicoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Médico no encontrado con id: " + req.getMedicoId()
                    ));
        }

        if (req.getMedicoDni() != null) {
            return medicoRepository.buscarPorDniActivo(req.getMedicoDni())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Médico no encontrado con DNI: " + req.getMedicoDni()
                    ));
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return medicoRepository.findByUsuario_Email(email)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Médico no encontrado (token)"
                ));
    }


    private Sede resolveSede(CrearReservaDTO req) {
        if (req.getSedeId() != null) {
            return sedeRepository.findById(req.getSedeId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
        }

        if (req.getSedeNombre() != null && !req.getSedeNombre().isBlank()) {
            return sedeRepository.findByNombreClinicaIgnoreCase(req.getSedeNombre())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada por nombre"));
        }

        if (req.getSedeDireccion() != null && !req.getSedeDireccion().isBlank()) {
            return sedeRepository.findByDireccionIgnoreCase(req.getSedeDireccion())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada por dirección"));
        }

        throw new IllegalArgumentException("Debe enviar información de sede");
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarReservasPacienteLogueado() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Paciente paciente = pacienteRepository.findByUsuario_Email(username)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));


        return reservaRepository.findReservasDtoByPacienteId(paciente.getId());
    }




    @Transactional(readOnly = true)
    public List<Reserva> reservasDePaciente(Long pacienteId) {
        // si aún quieres este método, puedes dejarlo así o eliminarlo si no lo usas
        return reservaRepository.findAll(); // o adaptar según tu necesidad
    }

    @Transactional
    public Reserva cancelar(Long reservaId) {
        Reserva r = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        r.setEstadoCita(false);
        return r;
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarActivos() {
        return reservaRepository.findByEstadoCitaTrue()
                .stream()
                .map(r -> new ReservaResponseDTO(
                        r.getId(),
                        r.getPaciente().getNombreUsuario(),
                        r.getPaciente().getDni(),
                        (r.getMedico() != null && r.getMedico().getUsuario() != null)
                                ? r.getMedico().getUsuario().getNombre()
                                : "Médico eliminado",
                        r.getMedico() != null ? r.getMedico().getDni() : null,
                        r.getSede() != null ? r.getSede().getNombreClinica() : null,
                        r.getFechaCreacion(),
                        r.getFechaCita(),
                        r.getHoraCita(),
                        r.getEstadoCita()
                ))
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public void eliminarLogico(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservación no encontrada"));
        reserva.setEstadoCita(false);
        reservaRepository.save(reserva);
    }
}
