package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecetaRepository  extends JpaRepository<Receta, Long> {

    @Query("""
       SELECT r FROM Receta r
       JOIN FETCH r.paciente p
       JOIN FETCH p.usuario u
       JOIN FETCH r.medico m
       JOIN FETCH m.usuario mu
       JOIN FETCH r.medicamento med
       WHERE p.id = :pacienteId
    """)
    List<Receta> listarPorPacienteConTodo(@Param("pacienteId") Long pacienteId);

    @Query("""
       SELECT r FROM Receta r
       JOIN FETCH r.paciente p
       JOIN FETCH p.usuario u
       JOIN FETCH r.medico m
       JOIN FETCH m.usuario mu
       JOIN FETCH r.medicamento med
       WHERE m.id = :medicoId
    """)
    List<Receta> listarPorMedicoConTodo(@Param("medicoId") Long medicoId);

    @Query("""
        SELECT r
        FROM Receta r
        JOIN FETCH r.paciente p
        JOIN FETCH p.usuario pu
        JOIN FETCH r.medico m
        JOIN FETCH m.usuario mu
        JOIN FETCH r.medicamento med
        WHERE p.dni = :dni
          AND r.estado = true
    """)
    List<Receta> findByPacienteDniConTodo(@Param("dni") Integer dni);

    List<Receta> findByMedico_Dni(Integer dni);



}
