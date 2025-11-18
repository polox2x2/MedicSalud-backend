package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.RecetaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Receta;
import org.springframework.stereotype.Component;

@Component
public class RecetaMapper {


    public RecetaResponseDTO mapToResponse(Receta receta) {
        return RecetaResponseDTO.builder()
                .pacienteId(receta.getPaciente().getId())
                .pacienteNombre(receta.getPaciente().getUsuario().getNombre() + "" + receta.getPaciente().getUsuario().getApellido())
                .medicoId(receta.getMedico().getId())
                .medicoNombre(receta.getMedico().getUsuario().getNombre())
                .medicamentoId(receta.getMedicamento().getId())
                .medicamentoNombre(receta.getMedicamento().getNombre())
                .dosis(receta.getDosis())
                .indicaciones(receta.getIndicaciones())
                .fechaCreacion(receta.getFechaCreacion())
                .build();
    }
}
