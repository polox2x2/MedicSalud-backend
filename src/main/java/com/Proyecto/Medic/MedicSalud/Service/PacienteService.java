package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Mappers.PacienteMapper;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;


    public PacienteDTO crearPacienteDesdeUsuarioDTO(UsuarioRequestDTO usuarioDTO) {

        // Validar que tiene rol PACIENTE
        boolean tieneRolPaciente = usuarioDTO.getRoles().stream()
                .anyMatch(r -> r.equalsIgnoreCase("PACIENTE"));

        if (!tieneRolPaciente) {
            throw new IllegalArgumentException("El usuario no tiene el rol PACIENTE");
        }
        // Buscar el Usuario real en base de datos
        Usuario usuario = usuarioRepository.findByDni(usuarioDTO.getDni())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si ya existe el paciente
        if (pacienteRepository.existsByUsuario(usuario)) {
            throw new IllegalStateException("Ya existe un paciente vinculado a este usuario");
        }
        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        paciente.setNombreUsuario(usuario.getNombre());
        paciente.setEstado(true);

        pacienteRepository.save(paciente);

        return PacienteMapper.toDTO(paciente);

    }

    public Optional<PacienteDTO> obtenerPacientePorUsuarioDTO(UsuarioRequestDTO usuarioDTO) {
        return usuarioRepository.findByDni(usuarioDTO.getDni())
                .flatMap(pacienteRepository::findByUsuario)
                .map(PacienteMapper::toDTO);
    }

    public List<PacienteDTO> listarActivos() {
        return pacienteRepository.findByEstadoTrue()
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
