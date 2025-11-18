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
import org.springframework.security.core.context.SecurityContextHolder;
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

        HorarioMedico horario = horarioMedicoMapper.toEntity(dto, medico);

        HorarioMedico guardado = horarioMedicoRepository.save(horario);

        return horarioMedicoMapper.toResponse(guardado);
    }

    public List<HorarioResponseDTO> listarPorEmail(String emailUsuario) {

        Medico medico = medicoRepository.findByUsuario_Email(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado para email: " + emailUsuario));

        return horarioMedicoRepository.findByMedico(medico)
                .stream()
                .map(horarioMedicoMapper::toResponse)
                .toList();
    }


}
