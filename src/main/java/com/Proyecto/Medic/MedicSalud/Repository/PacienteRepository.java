package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {
}
