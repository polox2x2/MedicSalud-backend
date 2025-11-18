package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaRepository  extends JpaRepository<Receta, Long> {

    List<Receta> findByPaciente_Id(Long pacienteId);

    List<Receta> findByMedico_Id(Long medicoId);

}
