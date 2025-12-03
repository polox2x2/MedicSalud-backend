package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteDTO;
import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;

public class PacienteMapper {

    // De Entidad → DTO
    public static PacienteDTO toDTO(Paciente paciente) {
        if (paciente == null)
            return null; // <- primero valida paciente

        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombreUsuario(paciente.getNombreUsuario());
        dto.setEstado(Boolean.TRUE.equals(paciente.getEstado()));

        Usuario u = paciente.getUsuario(); // <- toma una sola vez
        if (u != null) {
            dto.setDni(u.getDni());

            dto.setRol(seleccionarRolPrincipal(u.getRoles()));

        } else {
            dto.setDni(null);
            dto.setRol(null);
        }
        return dto;
    }

    public static PacienteResponseDTO toAllDTO(Paciente paciente) {
        if (paciente == null)
            return null;
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(paciente.getId());
        dto.setNombreUsuario(paciente.getNombreUsuario());
        dto.setEstado(Boolean.TRUE.equals(paciente.getEstado()));

        Usuario u = paciente.getUsuario();
        if (u != null) {
            dto.setDni(u.getDni());
            dto.setEmail(paciente.getUsuario().getEmail());
            dto.setRol(seleccionarRolPrincipal(u.getRoles()));
            dto.setTelefono(paciente.getUsuario().getTelefono());

        } else {
            dto.setDni(null);
            dto.setRol(null);
            dto.setEmail(null);
            dto.setTelefono(null);
        }

        return dto;
    }

    public static com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacientePerfilDTO toPerfilDTO(Paciente paciente) {
        if (paciente == null)
            return null;

        com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacientePerfilDTO dto = new com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacientePerfilDTO();
        dto.setId(paciente.getId());
        dto.setNombreUsuario(paciente.getNombreUsuario());
        dto.setEstado(Boolean.TRUE.equals(paciente.getEstado()));

        Usuario u = paciente.getUsuario();
        if (u != null) {
            dto.setDni(u.getDni());
            dto.setNombre(u.getNombre());
            dto.setApellido(u.getApellido());
            dto.setEmail(u.getEmail());
            dto.setTelefono(u.getTelefono());
            dto.setDireccion(u.getDireccion());
            dto.setFotoPerfil(u.getFotoPerfil());
            dto.setRol(seleccionarRolPrincipal(u.getRoles()));
        }

        return dto;
    }

    // De DTO → Entidad
    public static Paciente toEntity(PacienteDTO dto) {
        if (dto == null)
            return null;
        Paciente paciente = new Paciente();
        paciente.setId(dto.getId());
        paciente.setNombreUsuario(dto.getNombreUsuario());

        return paciente;

    }

    private static String seleccionarRolPrincipal(java.util.Set<Rol> roles) {
        if (roles == null || roles.isEmpty())
            return null;

        // normaliza la mayúsculas para comparar
        java.util.Set<String> nombres = roles.stream()
                .filter(java.util.Objects::nonNull)
                .map(Rol::getNombre)
                .filter(java.util.Objects::nonNull)
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(java.util.stream.Collectors.toSet());

        if (nombres.contains("PACIENTE"))
            return "PACIENTE";
        if (nombres.contains("MEDICO"))
            return "MEDICO";
        if (nombres.contains("ADMIN"))
            return "ADMIN";

        return nombres.iterator().next();
    }
}
