package com.Proyecto.Medic.MedicSalud.Service;


import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.ActualizarHistorialRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.CrearHistorialRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.HistorialResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Historial;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Mappers.HistorialMapper;
import com.Proyecto.Medic.MedicSalud.Repository.HistorialRepository;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HistorialService {

    private final HistorialRepository historialRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final HistorialMapper historialMapper;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public HistorialResponseDTO crearHistorial(CrearHistorialRequestDTO dto, String token) {

        String realToken = token.replace("Bearer ", "");
        String emailMedico = jwtService.extractUsername(realToken);

        Usuario usuarioMedico = usuarioRepository.findByEmail(emailMedico)
                .orElseThrow(() -> new RuntimeException("Usuario médico no encontrado"));

        Medico medico = medicoRepository.findByUsuario_Id(usuarioMedico.getId())
                .orElseThrow(() -> new RuntimeException("El usuario no está registrado como médico"));


        Paciente paciente = pacienteRepository.findByDniAndEstadoTrue(dto.getPacienteDni())
                .orElseThrow(() -> new RuntimeException("Paciente con DNI " + dto.getPacienteDni() + " no encontrado"));

        Historial historial = historialMapper.toEntity(dto, paciente, medico);
        historialRepository.save(historial);

        return historialMapper.toResponseDTO(historial);
    }




    public List<HistorialResponseDTO> listarActivos() {
        return historialRepository.findActivosConPacienteYMedico()
                .stream()
                .map(historialMapper::toResponseDTO)
                .toList();
    }


    public List<HistorialResponseDTO> listarPorPacienteDni(Integer dni) {
        return historialRepository.findByPacienteDniAndEstadoTrueConJoin(dni)
                .stream()
                .map(historialMapper::toResponseDTO)
                .toList();
    }

    public HistorialResponseDTO obtenerPorId(Long id) {
        Historial historial = historialRepository.findByIdAndEstadoTrue(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado o inactivo"));
        return historialMapper.toResponseDTO(historial);
    }

    public HistorialResponseDTO actualizarHistorial(Long id, ActualizarHistorialRequestDTO dto) {
        Historial historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        historialMapper.updateEntity(dto, historial);
        return historialMapper.toResponseDTO(historial);
    }

    // Eliminación lógica
    public void eliminarLogico(Long id) {
        Historial historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        historial.setEstado(false);
    }



    }




