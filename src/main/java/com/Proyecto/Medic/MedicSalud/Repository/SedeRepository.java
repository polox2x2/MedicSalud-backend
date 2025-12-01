package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SedeRepository extends JpaRepository<Sede,Long> {
    Optional<Sede> findByNombreClinicaIgnoreCase(String nombreClinica);
    Optional<Sede> findByDireccionIgnoreCase(String direccion);
    Optional<Sede>findByNombreClinica(String nombreClinica);
}
