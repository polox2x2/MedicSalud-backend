package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.Horario.CrearHorarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Horario.HorarioResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import org.springframework.stereotype.Component;

import com.Proyecto.Medic.MedicSalud.Entity.EstadoCita;

@Component
public class HorarioMedicoMapper {

    public HorarioResponseDTO toResponse(HorarioMedico h) {

        boolean ocupado = h.getReservas() != null && h.getReservas().stream()
                .anyMatch(r -> r.getEstadoCita() != EstadoCita.RECHAZADA);

        String estado;
        if (!h.getMedico().getEstado()) {
            estado = "NO DISPONIBLE";
        } else if (ocupado) {
            estado = "OCUPADO";
        } else {
            estado = "DISPONIBLE";
        }

        return new HorarioResponseDTO(
                h.getId(),
                h.getFecha(),
                h.getHoraInicio().toString(),
                h.getHoraFin().toString(),
                h.getMedico().getDni().toString(),
                estado);
    }

    public static HorarioMedico toEntity(CrearHorarioRequestDTO dto, Medico medico) {
        HorarioMedico entity = new HorarioMedico();
        entity.setFecha(dto.getFecha());
        entity.setHoraInicio(dto.getHoraInicio());
        entity.setHoraFin(dto.getHoraFin());
        entity.setMedico(medico);
        return entity;
    }

}
