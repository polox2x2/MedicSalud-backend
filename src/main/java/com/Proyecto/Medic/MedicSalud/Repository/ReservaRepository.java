package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {


    boolean existsByMedicoIdAndFechaCitaAndHoraCitaAndEstadoCitaTrue(
            Long medicoId, LocalDate fechaCita, LocalTime horaCita
    );
    List<Reserva> findByPacienteIdOrderByFechaCitaDesc(Long pacienteId);

    List<Reserva> findByMedicoIdAndFechaCitaOrderByHoraCitaAsc(Long medicoId, LocalDate fechaCita);

    List<Reserva> findBySedeIdAndFechaCitaOrderByHoraCitaAsc(Long sedeId, LocalDate fechaCita);


}
