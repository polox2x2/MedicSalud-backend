package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Long> {
    List<HorarioMedico> findByMedico(Medico medico);

    boolean existsByMedicoAndFecha(Medico medico, java.time.LocalDate fecha);

    List<HorarioMedico> findByMedicoAndFecha(Medico medico, java.time.LocalDate fecha);

    List<HorarioMedico> findByMedicoAndActivoTrue(Medico medico);
}
