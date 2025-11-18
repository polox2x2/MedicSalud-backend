package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Long> {
    List<HorarioMedico> findByMedico(Medico medico);

    boolean existsByMedicoAndDia(Medico medico, DayOfWeek dia);
}
