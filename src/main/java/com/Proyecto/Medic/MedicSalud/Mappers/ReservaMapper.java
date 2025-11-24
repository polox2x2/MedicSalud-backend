package com.Proyecto.Medic.MedicSalud.Mappers;

import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import com.Proyecto.Medic.MedicSalud.Entity.Sede;

public class ReservaMapper {

    // Entidad → DTO de respuesta
    public static ReservaResponseDTO toResponse(Reserva r) {
        if (r == null) return null;

        String nombrePaciente = null;
        String nombreMedico   = null;
        String nombreSede     = null;

        Integer pacienteDni = null;
        Integer medicoDni   = null;

        // Paciente
        Paciente p = r.getPaciente();
        if (p != null) {
            nombrePaciente = p.getNombreUsuario();
            pacienteDni    = p.getDni();
        }

        // Médico
        Medico m = r.getMedico();
        if (m != null) {
            if (m.getUsuario() != null) {
                nombreMedico = m.getUsuario().getNombre();
            }
            medicoDni = m.getDni();
        }

        // Sede
        Sede s = r.getSede();
        if (s != null) {
            nombreSede = s.getNombreClinica();
        }

        return ReservaResponseDTO.builder()
                .id(r.getId())
                .nombrePaciente(nombrePaciente)
                .pacienteDni(pacienteDni)
                .nombreMedico(nombreMedico)
                .medicoDni(medicoDni)
                .nombreSede(nombreSede)
                .fechaCreacion(r.getFechaCreacion())
                .fechaCita(r.getFechaCita())
                .horaCita(r.getHoraCita())
                .estadoCita(r.getEstadoCita())
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
