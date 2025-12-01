package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Historial;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HistorialRepository extends JpaRepository<Historial,Long> {



    // Todos los historiales activos
    List<Historial> findByEstadoTrue();

    // Historiales activos por DNI de paciente
    List<Historial> findByPaciente_DniAndEstadoTrue(Integer dni);

    // Por si necesitas todos (activos e inactivos) por DNI
    List<Historial> findByPaciente_Dni(Integer dni);

    Optional<Historial> findByIdAndEstadoTrue(Long id);



    @Query("""
           SELECT h FROM Historial h
           JOIN FETCH h.paciente p
           JOIN FETCH p.usuario pu
           LEFT JOIN FETCH h.medico m
           LEFT JOIN FETCH m.usuario mu
           WHERE h.estado = true
           """)
    List<Historial> findActivosConPacienteYMedico();


    @Query("""
           SELECT h FROM Historial h
           JOIN FETCH h.paciente p
           JOIN FETCH p.usuario pu
           LEFT JOIN FETCH h.medico m
           LEFT JOIN FETCH m.usuario mu
           WHERE h.estado = true
           AND p.dni = :dni
           """)
    List<Historial> findByPacienteDniAndEstadoTrueConJoin(@Param("dni") Integer dni);


}
