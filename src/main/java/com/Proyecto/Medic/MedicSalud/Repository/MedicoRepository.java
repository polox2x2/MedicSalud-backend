package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long> {

    Optional<Medico> findByUsuario_Email(String email);

    List<Medico>findByEstadoTrue ();
    List<Medico>findAll();
    Optional<Medico> findByDni(Integer dni);


    @Query("SELECT m FROM Medico m WHERE m.dni = :dni AND m.estado = true")
    Optional<Medico> buscarPorDniActivo(@Param("dni") Integer dni);
}
