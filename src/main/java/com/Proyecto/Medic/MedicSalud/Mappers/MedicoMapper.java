package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.MedicoRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.RegistroMedicoDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;

public class MedicoMapper {

    public Medico toEntity(RegistroMedicoDTO dto, Usuario usuario) {
        if (dto == null || usuario == null) {
            return null;
        }
        Medico medico = new Medico();
        medico.setUsuario(usuario);
        medico.setEspecialidad(dto.getEspecialidad());
        medico.setDni(dto.getDni());
        medico.setEstado(dto.getEstado());
        return medico;
    }

    public RegistroMedicoDTO toDTO(Medico medico) {
        if (medico == null) {
            return null;
        }
        return RegistroMedicoDTO.builder()
                .estado(medico.getEstado())
                .especialidad(medico.getEspecialidad())
                .dni(medico.getDni())
                .build();
    }

    public static MedicoRequestDTO listaMedicoDTO(Medico medico) {
        return MedicoRequestDTO.builder()
                .id(medico.getId())
                .nombre(medico.getUsuario().getNombre() + medico.getUsuario().getApellido())
                .correo(medico.getUsuario().getEmail())
                .dni(medico.getUsuario().getDni())
                .especialidad(medico.getEspecialidad())
                .telefono(medico.getTelefono())
                .build();
    }
}
