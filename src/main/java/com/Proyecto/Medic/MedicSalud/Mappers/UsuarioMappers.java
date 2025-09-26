package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.RegistroUsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;

import java.time.LocalDate;

public class UsuarioMappers {

    public static UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .estado(usuario.isEstado())
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
                .fechaNacimiento(LocalDate.from(dto.getFechanaciemiento()))
                .estado(true)
                .build();
    }
}

