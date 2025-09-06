package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioMappers {

    public static UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .estado(usuario.isEstado())
                .roles(usuario.getRoles().stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .estado(dto.isEstado())
                .build();
    }
    public static Usuario RequestiUsuarioDTO(UsuarioRequestDTO request, Set<Rol> roles) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setDni(request.getDni());
        usuario.setEmail(request.getEmail());
        usuario.setClave(request.getClave());
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());

        usuario.setEstado(true); // por defecto activo
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setRoles(roles);

        return usuario;
    }


}

