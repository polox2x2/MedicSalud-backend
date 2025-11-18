package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.RegistroUsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioUpDateDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class UsuarioMappers {

    public static UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .especialidad(usuario.getEspecialidad())
                .roles(usuario.getRoles() != null ? usuario.getRoles().stream().map(rol -> rol.getNombre()).collect(Collectors.toSet()) : java.util.Collections.emptySet())
                .estado(usuario.getEstado())
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
    public static Usuario registerUsuarioDTO (RegistroUsuarioDTO dto){
        return Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .dni(dto.getDni())
                .email(dto.getEmail())
                .clave(dto.getClave())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .especialidad(dto.getEspecialidad())
                .fechaNacimiento(dto.getFechaNacimiento())
                .estado(true)
                .build();
    }
    public static RegistroUsuarioDTO listarUsuarioDTO (Usuario usuario){
        return RegistroUsuarioDTO.builder()
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .dni(usuario.getDni())
                .email(usuario.getEmail())
                .clave(usuario.getClave())
                .direccion(usuario.getDireccion())
                .telefono(usuario.getTelefono())
                .estado(usuario.getEstado())
                .build();
    }
    public static UsuarioUpDateDTO actualizarUsuario (Usuario usuario){
        return UsuarioUpDateDTO.builder()
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .fotoPerfil(usuario.getFotoPerfil())
                .build();
    }


}

