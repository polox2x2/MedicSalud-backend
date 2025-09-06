package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Mappers.UsuarioMappers;
import com.Proyecto.Medic.MedicSalud.Repository.RolRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public List<UsuarioDTO> listarActivos() {
        return usuarioRepository.findByEstadoTrue()
                .stream()
                .map(UsuarioMappers::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioMappers::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public UsuarioDTO crear(UsuarioRequestDTO request) {
        // buscar roles desde la BD usando los nombres recibidos
        Set<Rol> roles = request.getRoles().stream()
                .map(nombre -> rolRepository.findByNombre(nombre)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombre)))
                .collect(Collectors.toSet());
        Usuario usuario = UsuarioMappers.RequestiUsuarioDTO(request, roles);

        return UsuarioMappers.toDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        return UsuarioMappers.toDTO(usuarioRepository.save(usuario));
    }

    public void eliminarLogico(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(false); // baja lógica
        usuarioRepository.save(usuario);
    }


}
