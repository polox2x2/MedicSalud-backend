package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.CrearRecetaRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.RecetaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.*;
import com.Proyecto.Medic.MedicSalud.Mappers.RecetaMapper;
import com.Proyecto.Medic.MedicSalud.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository recetaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final RecetaMapper recetaMapper;

    @Transactional
    public RecetaResponseDTO crearReceta(CrearRecetaRequestDTO dto) {

        var paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        var medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        var medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        var receta = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(medicamento)
                .dosis(dto.getDosis())
                .indicaciones(dto.getIndicaciones())
                .fechaCreacion(LocalDateTime.now())
                .build();

        recetaRepository.save(receta);

        return recetaMapper.mapToResponse(receta);
    }

    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarPorPaciente(Long pacienteId) {
        return recetaRepository.findByPaciente_Id(pacienteId)
                .stream()
                .map(recetaMapper::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarPorMedico(Long medicoId) {
        return recetaRepository.findByMedico_Id(medicoId)
                .stream()
                .map(recetaMapper::mapToResponse)
                .toList();
    }
}


