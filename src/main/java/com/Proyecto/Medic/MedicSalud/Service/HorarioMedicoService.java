package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.Horario.CrearHorarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Horario.HorarioResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Mappers.HorarioMedicoMapper;
import com.Proyecto.Medic.MedicSalud.Repository.HorarioMedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioMedicoService {

    private final HorarioMedicoRepository horarioMedicoRepository;
    private final MedicoRepository medicoRepository;
    private final HorarioMedicoMapper horarioMedicoMapper;

    public HorarioResponseDTO crearHorario(CrearHorarioRequestDTO dto, String emailUsuario) {

        Medico medico = medicoRepository.findByUsuario_Email(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado para email: " + emailUsuario));

        // Validar que la hora de inicio sea antes que la hora de fin
        if (dto.getHoraInicio().isAfter(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser antes que la hora de fin");
        }

        // Validar que no exista un horario solapado para el mismo médico y fecha
        boolean existeSolapamiento = horarioMedicoRepository
                .findByMedicoAndFecha(medico, dto.getFecha())
                .stream()
                .anyMatch(h -> (dto.getHoraInicio().isBefore(h.getHoraFin()) &&
                        dto.getHoraFin().isAfter(h.getHoraInicio())));

        if (existeSolapamiento) {
            throw new IllegalArgumentException(
                    "El médico ya tiene un horario asignado en ese rango de horas para la fecha seleccionada");
        }

        HorarioMedico horario = HorarioMedicoMapper.toEntity(dto, medico);
        horario.setActivo(true); // Por defecto activo al crear

        HorarioMedico guardado = horarioMedicoRepository.save(horario);

        return horarioMedicoMapper.toResponse(guardado);
    }

    public List<HorarioResponseDTO> listarPorEmail(String emailUsuario) {

        Medico medico = medicoRepository.findByUsuario_Email(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado para email: " + emailUsuario));

        return horarioMedicoRepository.findByMedicoAndActivoTrue(medico)
                .stream()
                .map(horarioMedicoMapper::toResponse)
                .toList();
    }

    @Transactional
    public HorarioResponseDTO editarHorario(Long id, CrearHorarioRequestDTO dto) {
        HorarioMedico horario = horarioMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        Medico medico = horario.getMedico();

        horario.setFecha(dto.getFecha());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFin(dto.getHoraFin());

        // Validar que la hora de inicio sea antes que la hora de fin
        if (dto.getHoraInicio().isAfter(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser antes que la hora de fin");
        }


        boolean existeSolapamiento = horarioMedicoRepository
                .findByMedicoAndFecha(medico, dto.getFecha())
                .stream()
                .filter(h -> !h.getId().equals(id)) // Exclude the current horario being edited
                .anyMatch(h -> (dto.getHoraInicio().isBefore(h.getHoraFin()) &&
                        dto.getHoraFin().isAfter(h.getHoraInicio())));

        if (existeSolapamiento) {
            throw new IllegalArgumentException(
                    "El médico ya tiene un horario asignado en ese rango de horas para la fecha seleccionada");
        }

        HorarioMedico actualizado = horarioMedicoRepository.save(horario);
        return horarioMedicoMapper.toResponse(actualizado);
    }

    @Transactional
    public void eliminarHorario(Long id) {
        HorarioMedico horario = horarioMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        horario.setActivo(false);
        horarioMedicoRepository.save(horario);
    }

}
