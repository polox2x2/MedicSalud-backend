package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    Optional<Paciente> findByUsuario(Usuario usuario);

    boolean existsByUsuario(Usuario usuario);

    List<Paciente> findByEstadoTrue();

    @Query("""
        SELECT p FROM Paciente p
        LEFT JOIN FETCH p.usuario u
        LEFT JOIN FETCH u.roles r
        WHERE p.estado = true
    """)
    List<Paciente> findActivosConUsuario();

    @Query("""
        SELECT p FROM Paciente p
        LEFT JOIN FETCH p.usuario u
        WHERE p.estado = false
    """)
    List<Paciente> findInactivosConUsuario();

    @Query("""
        SELECT p FROM Paciente p
        LEFT JOIN FETCH p.usuario u
        WHERE p.id = :id
    """)
    Optional<Paciente> findByIdConUsuario(Long id);

    @Query("""
    SELECT DISTINCT p FROM Paciente p
    LEFT JOIN FETCH p.usuario u
    LEFT JOIN FETCH u.roles r
    WHERE p.estado = true
""")
    List<Paciente> findActivosConUsuarioYRoles();

    @Query("""
    SELECT p
    FROM Paciente p
    JOIN FETCH p.usuario u         
    WHERE p.estado = true
""")
    List<Paciente> findActivosConUsuarioObligatorio();

    @Query("""
    SELECT new com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteDTO(
        p.id,
        p.nombreUsuario,
        u.dni,
        null,           
        p.estado
    )
    FROM Paciente p
    JOIN p.usuario u               
    WHERE p.estado = true
""")
    List<PacienteDTO> findActivosDTOConDni();

}

