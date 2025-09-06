package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long> {




}
