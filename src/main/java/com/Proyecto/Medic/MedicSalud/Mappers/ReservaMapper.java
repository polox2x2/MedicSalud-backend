package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Sede;

public class ReservaMapper {

    // Entidad → DTO de respuesta
    public static ReservaResponseDTO toResponse(Reserva r) {
        if (r == null) return null;

        String nombrePaciente = null;
        String nombreMedico   = null;
        String nombreSede     = null;

        //Usuario
        Paciente p = r.getPaciente();
        if (p != null){
            nombrePaciente = p.getNombreUsuario();
        }
        //Medico
        Medico m = r.getMedico();
        if (m != null){
            nombreMedico = m.getUsuario().getNombre();
        }

        //Sede
        Sede s = r.getSede();
        if (s != null){
            nombreSede = s.getNombreClinica();
        }
        return ReservaResponseDTO.builder()
                .id(r.getId())
                .nombrePaciente(nombrePaciente)
                .nombreMedico(nombreMedico)
                .nombreSede(nombreSede)
                .estadoCita(r.getEstadoCita())
                .fechaCreacion(r.getFechaCreacion())
                .fechaCita(r.getFechaCita())
                .horaCita(r.getHoraCita())
                .build();
    }

    // DTO crear → Entidad
    public static Reserva toEntityFromCrear(CrearReservaDTO dto) {
        if (dto == null) return null;

        Reserva r = new Reserva();
        r.setFechaCita(dto.getFechaCita());
        r.setHoraCita(dto.getHoraCita());
        return r;
    }

}