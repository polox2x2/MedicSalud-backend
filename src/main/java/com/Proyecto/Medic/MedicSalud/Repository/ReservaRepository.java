package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByEstadoCitaTrue();

    boolean existsByMedico_IdAndFechaCitaAndHoraCitaAndEstadoCitaTrue(
            Long medicoId,
            LocalDate fechaCita,
            LocalTime horaCita
    );

    List<Reserva> findByMedico_IdAndFechaCitaOrderByHoraCitaAsc(Long medicoId, LocalDate fechaCita);



    @Query("""
        SELECT new com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO(
            r.id,
            p.nombreUsuario,
            p.dni,
            COALESCE(u.nombre, 'Médico eliminado'),
            m.dni,
            s.nombreClinica,
            r.fechaCreacion,
            r.fechaCita,
            r.horaCita,
            r.estadoCita
        )
        FROM Reserva r
        JOIN r.paciente p
        LEFT JOIN r.medico m
        LEFT JOIN m.usuario u
        LEFT JOIN r.sede s
        WHERE p.id = :pacienteId
        ORDER BY r.fechaCita DESC
        """)
    List<ReservaResponseDTO> findReservasDtoByPacienteId(@Param("pacienteId") Long pacienteId);




}
