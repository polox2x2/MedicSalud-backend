package com.Proyecto.Medic.MedicSalud.Mappers;


import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.ActualizarHistorialRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.CrearHistorialRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.HistorialResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Historial;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import org.springframework.stereotype.Component;

@Component
public class HistorialMapper {

    public Historial toEntity(CrearHistorialRequestDTO dto, Paciente paciente, Medico medico) {
        return Historial.builder()
                .diagnostico(dto.getDiagnostico())
                .tratamiento(dto.getTratamiento())
                .observaciones(dto.getObservaciones())
                .paciente(paciente)
                .medico(medico)
                .estado(true)
                .build();
    }

    public void updateEntity(ActualizarHistorialRequestDTO dto, Historial historial) {
        if (dto.getDiagnostico() != null) {
            historial.setDiagnostico(dto.getDiagnostico());
        }
        if (dto.getTratamiento() != null) {
            historial.setTratamiento(dto.getTratamiento());
        }
        if (dto.getObservaciones() != null) {
            historial.setObservaciones(dto.getObservaciones());
        }
        if (dto.getEstado() != null) {
            historial.setEstado(dto.getEstado());
        }
    }

    public HistorialResponseDTO toResponseDTO(Historial historial) {

        Long medicoId = null;
        String medicoNombre = null;

        if (historial.getMedico() != null) {
            medicoId = historial.getMedico().getId();
            // si puede no tener usuario, protege también aquí
            if (historial.getMedico().getUsuario() != null) {
                medicoNombre = historial.getMedico().getUsuario().getNombre();
            }
        }

        return HistorialResponseDTO.builder()
                .id(historial.getId())
                .fechaRegistro(historial.getFechaRegistro())
                .diagnostico(historial.getDiagnostico())
                .tratamiento(historial.getTratamiento())
                .observaciones(historial.getObservaciones())
                .pacienteDni(historial.getPaciente().getDni())
                .pacienteNombre(historial.getPaciente().getUsuario().getNombre())
                .estado(historial.getEstado())
                .pacienteId(historial.getId())
                .medicoId(medicoId)
                .medicoNombre(medicoNombre)
                .build();
    }

}
