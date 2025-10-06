package com.Proyecto.Medic.MedicSalud.Mappers;


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
        medico.setEstado(dto.getEstado());
        return medico;
    }

    public RegistroMedicoDTO toDTO(Medico medico) {
        if (medico == null) {
            return null;
        }
        return RegistroMedicoDTO.builder()
                .estado(medico.getEstado())
                .build();
    }
}


