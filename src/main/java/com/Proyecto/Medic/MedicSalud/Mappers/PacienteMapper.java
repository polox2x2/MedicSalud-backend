package com.Proyecto.Medic.MedicSalud.Mappers;


import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;

public class PacienteMapper {


    // De Entidad → DTO
    public static PacienteDTO toDTO(Paciente paciente) {
        if (paciente == null) return null;
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombreUsuario(paciente.getNombreUsuario());
        dto.setDniUsuario(paciente.getUsuario().getDni());
        dto.setEstado(paciente.getEstado());
        return dto;
    }

    // De DTO → Entidad
    public static Paciente toEntity(PacienteDTO dto) {
        if (dto == null) return null;
        Paciente paciente = new Paciente();
        paciente.setId(dto.getId());
        paciente.setNombreUsuario(dto.getNombreUsuario());
        // El usuario se debería setear desde el servicio
        return paciente;

    }
}
