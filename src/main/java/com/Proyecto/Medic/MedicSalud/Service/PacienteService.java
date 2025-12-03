package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteDTO;
import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteResponseDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioUpDateDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Mappers.PacienteMapper;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        paciente.setDni(usuario.getDni());
        paciente.setEstado(true);

        pacienteRepository.save(paciente);

        return PacienteMapper.toDTO(paciente);

    }

    public Optional<PacienteDTO> obtenerPacientePorUsuarioDTO(UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findByDni(usuarioDTO.getDni())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado por DNI"));

        return pacienteRepository.findByUsuario(usuario).map(PacienteMapper::toDTO);
    }

    @Transactional()
    public List<PacienteDTO> listarActivos() {
        return pacienteRepository.findByEstadoTrue()
                .stream()
                .map(PacienteMapper::toDTO)
                .toList();
    }

    @Transactional
    public List<PacienteResponseDTO> listaCompleta() {
        return pacienteRepository.findAll().stream().map(PacienteMapper::toAllDTO).toList();
    }

    public PacienteDTO buscarPorId(Long id) {
        return pacienteRepository.findByIdConUsuario(id)
                .map(PacienteMapper::toDTO)
                .orElse(null);
    }

    public PacienteDTO guardar(PacienteDTO dto) {

        var entidad = PacienteMapper.toEntity(dto);

        if (dto.getDni() != null) {
            Usuario u = usuarioRepository.findByDni(dto.getDni())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado por DNI"));
            entidad.setUsuario(u);
            if (entidad.getNombreUsuario() == null) {
                entidad.setNombreUsuario(u.getNombre());
            }
        }

        var guardado = pacienteRepository.save(entidad);
        return PacienteMapper.toDTO(guardado);
    }

    public void eliminarLogicoPorDni(Integer dni) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con DNI: " + dni));

        Paciente paciente = pacienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado para el usuario con DNI: " + dni));

        usuario.setEstado(false);
        paciente.setEstado(false);

        usuarioRepository.save(usuario);
        pacienteRepository.save(paciente);
    }

    public PacienteDTO actualizarPaciente(Long id, UsuarioUpDateDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Usuario usuario = paciente.getUsuario();

        actualizarDatosUsuario(usuario, dto);

        return PacienteMapper.toDTO(paciente);
    }

    public PacienteDTO actualizarPacientePorEmail(String email, UsuarioUpDateDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Paciente paciente = pacienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado para este usuario"));

        actualizarDatosUsuario(usuario, dto);

        // Sincronizar nombreUsuario en Paciente si cambió el nombre
        if (dto.getNombre() != null || dto.getApellido() != null) {
            paciente.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido());
            pacienteRepository.save(paciente);
        }

        return PacienteMapper.toDTO(paciente);
    }

    public com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacientePerfilDTO obtenerMiPerfil(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Paciente paciente = pacienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado para este usuario"));

        return PacienteMapper.toPerfilDTO(paciente);
    }

    private void actualizarDatosUsuario(Usuario usuario, UsuarioUpDateDTO dto) {
        if (dto.getNombre() != null)
            usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null)
            usuario.setApellido(dto.getApellido());
        if (dto.getEmail() != null)
            usuario.setEmail(dto.getEmail());
        if (dto.getTelefono() != null)
            usuario.setTelefono(dto.getTelefono());
        if (dto.getDireccion() != null)
            usuario.setDireccion(dto.getDireccion());

        usuarioRepository.save(usuario);
    }

}
