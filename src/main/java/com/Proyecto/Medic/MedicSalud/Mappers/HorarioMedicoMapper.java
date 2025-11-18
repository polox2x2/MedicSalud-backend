package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.Horario.CrearHorarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Horario.HorarioResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import org.springframework.stereotype.Component;


@Component
public class HorarioMedicoMapper {


    public HorarioResponseDTO toResponse(HorarioMedico h) {

        return new HorarioResponseDTO(
                h.getId(),
                h.getDia().name(),
                h.getHoraInicio().toString(),
                h.getHoraFin().toString()
        );
    }
    public static HorarioMedico toEntity(CrearHorarioRequestDTO dto, Medico medico) {
        HorarioMedico entity = new HorarioMedico();
        entity.setDia(dto.getDia());
        entity.setHoraInicio(dto.getHoraInicio());
        entity.setHoraFin(dto.getHoraFin());
        entity.setMedico(medico);
        return entity;
    }



}
