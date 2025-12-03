package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.BloqueHorarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.DisponibilidadMedicoDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.MedicoRequestDTO;
import com.Proyecto.Medic.MedicSalud.Entity.EstadoCita;
import com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import com.Proyecto.Medic.MedicSalud.Mappers.MedicoMapper;
import com.Proyecto.Medic.MedicSalud.Repository.HorarioMedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.RecetaRepository;
import com.Proyecto.Medic.MedicSalud.Repository.ReservaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicoService {

        private final MedicoRepository medicoRepository;
        private final RecetaRepository recetaRepo;
        private final ReservaRepository reservaRepository;
        private final HorarioMedicoRepository horarioMedicoRepository;

        public List<Medico> listaCompleta() {
                return medicoRepository.findAll();
        }

        public List<MedicoRequestDTO> listaActivos() {
                return medicoRepository.findByEstadoTrue()
                                .stream()
                                .map(MedicoMapper::listaMedicoDTO)
                                .collect(Collectors.toList());
        }

        @Transactional
        public DisponibilidadMedicoDTO obtenerDisponibilidad(Long medicoId, LocalDate fecha) {
                Medico medico = medicoRepository.findById(medicoId)
                                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

                // Obtener horarios del médico para la fecha específica
                List<HorarioMedico> horarios = horarioMedicoRepository.findByMedicoAndFecha(medico, fecha);

                List<BloqueHorarioDTO> bloques = new ArrayList<>();

                for (HorarioMedico horario : horarios) {
                        LocalTime inicio = horario.getHoraInicio();
                        LocalTime fin = horario.getHoraFin();

                        while (inicio.isBefore(fin)) {
                                LocalTime finBloque = inicio.plusMinutes(30);
                                if (finBloque.isAfter(fin))
                                        break;

                                boolean ocupado = false;

                                String estado = ocupado ? "OCUPADO" : "DISPONIBLE";

                                bloques.add(new BloqueHorarioDTO(inicio.toString(), ocupado, estado));
                                inicio = finBloque;
                        }
                }

                return DisponibilidadMedicoDTO.builder()
                                .medicoId(medico.getId())
                                .nombreMedico(medico.getUsuario().getNombre())
                                .especialidad(medico.getEspecialidad())
                                .nombreSede(medico.getSede() != null ? medico.getSede().getNombreClinica() : null)
                                .fecha(fecha)
                                .horarios(bloques)
                                .build();
        }

        @Transactional
        public MedicoRequestDTO actualizarMedicoLogueado(
                        com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.ActualizarMedicoDTO dto) {
                String email = org.springframework.security.core.context.SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getName();

                Medico medico = medicoRepository.findByUsuario_Email(email)
                                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

                // Actualizar datos del médico
                medico.setTelefono(dto.getTelefono());

                // Actualizar datos del usuario asociado
                com.Proyecto.Medic.MedicSalud.Entity.Usuario usuario = medico.getUsuario();
                usuario.setEmail(dto.getEmail());
                usuario.setTelefono(dto.getTelefono());

                medicoRepository.save(medico);

                return MedicoMapper.listaMedicoDTO(medico);
        }

        public MedicoRequestDTO obtenerMedicoLogueado() {
                String email = org.springframework.security.core.context.SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getName();

                Medico medico = medicoRepository.findByUsuario_Email(email)
                                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

                return MedicoMapper.listaMedicoDTO(medico);
        }
}
