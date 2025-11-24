package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.ActualizarRecetaRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.CrearRecetaRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.RecetaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.*;
import com.Proyecto.Medic.MedicSalud.Mappers.RecetaMapper;
import com.Proyecto.Medic.MedicSalud.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        System.out.println("DEBUG Receta -> pacienteId=" + dto.getPacienteId()
                + ", medicoId=" + dto.getMedicoId()
                + ", medicamentoId=" + dto.getMedicamentoId());

        var paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException(
                        "Paciente no encontrado. ID=" + dto.getPacienteId()));

        var medico = medicoRepository.buscarPorIdActivo(dto.getMedicoId())
                .orElseThrow(() -> new RuntimeException(
                        "Médico no encontrado. ID=" + dto.getMedicoId()));

        var medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException(
                        "Medicamento no encontrado. ID=" + dto.getMedicamentoId()));

        var receta = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(medicamento)
                .dosis(dto.getDosis())
                .indicaciones(dto.getIndicaciones())
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .build();

        recetaRepository.save(receta);

        return recetaMapper.mapToResponse(receta);
    }

    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarPorPaciente(Long pacienteId) {
        return recetaRepository.listarPorPacienteConTodo(pacienteId)
                .stream()
                .map(recetaMapper::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarPorMedico(Long medicoId) {
        return recetaRepository.listarPorMedicoConTodo(medicoId)
                .stream()
                .map(recetaMapper::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
        public List<RecetaResponseDTO> listarPorPacienteDni(Integer dni) {
        return recetaRepository.findByPacienteDniConTodo(dni)
                .stream()
                .map(recetaMapper::mapToResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarPorMedicoDni(Integer dni) {
        return recetaRepository.findByMedico_Dni(dni)
                .stream()
                .map(recetaMapper::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarRecetasPacienteActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // correo / username del token

        Paciente paciente = pacienteRepository.findByUsuario_Email(username)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado para el usuario autenticado"));

        return listarPorPaciente(paciente.getId());
    }

    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarRecetasMedicoActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Medico medico = medicoRepository.findByUsuario_Email(username)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado para el usuario autenticado"));

        return listarPorMedico(medico.getId());
    }


    @Transactional
    public RecetaResponseDTO actualizarReceta(Long recetaId, ActualizarRecetaRequestDTO dto) {

        var receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        if (Boolean.FALSE.equals(receta.getEstado())) {
            throw new RuntimeException("La receta está desactivada");
        }

        var medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        receta.setMedicamento(medicamento);
        receta.setDosis(dto.getDosis());
        receta.setIndicaciones(dto.getIndicaciones());

        recetaRepository.save(receta);

        return recetaMapper.mapToResponse(receta);
    }


    @Transactional
    public void eliminarRecetaLogica(Long recetaId) {
        var receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        if (Boolean.FALSE.equals(receta.getEstado())) {
            return;
        }

        receta.setEstado(false);
        recetaRepository.save(receta);
    }


}


