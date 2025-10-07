package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEstadoTrue();
    boolean existsByEmail(String emain);
    boolean existsByDni (Integer dni);
    List<Usuario>findAll();

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByDni(Integer dni);



}
