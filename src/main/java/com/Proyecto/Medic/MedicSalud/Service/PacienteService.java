package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Mappers.PacienteMapper;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;


    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PacienteDTO buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .map(PacienteMapper::toDTO)
                .orElse(null);
    }

    public PacienteDTO guardar(PacienteDTO dto) {
        Paciente paciente = PacienteMapper.toEntity(dto);
        Paciente guardado = pacienteRepository.save(paciente);
        return PacienteMapper.toDTO(guardado);
    }

    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);

    }
}
