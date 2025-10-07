package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {


    Optional<Paciente> findByUsuario(Usuario usuario);


    boolean existsByUsuario(Usuario usuario);


    List<Paciente> findByEstadoTrue();

}
