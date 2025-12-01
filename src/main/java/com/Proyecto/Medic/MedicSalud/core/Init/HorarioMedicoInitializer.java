package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Repository.HorarioMedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(6)
public class HorarioMedicoInitializer implements CommandLineRunner {

    private final MedicoRepository medicoRepository;
    private final HorarioMedicoRepository horarioMedicoRepository;

    @Override
    public void run(String... args) {

        if (horarioMedicoRepository.count() > 0) {
            log.info(">>> Horarios médicos ya inicializados.");
            return;
        }

        List<Medico> medicos = medicoRepository.findAll();
        if (medicos.isEmpty()) {
            throw new IllegalStateException("No existen médicos para asignar horarios.");
        }

        for (Medico medico : medicos) {

            // Lunes
            saveHorario(medico, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(13, 0));
            saveHorario(medico, DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(18, 0));

            // Martes
            saveHorario(medico, DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(13, 0));
            saveHorario(medico, DayOfWeek.TUESDAY, LocalTime.of(15, 0), LocalTime.of(19, 0));

            // Miércoles
            saveHorario(medico, DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));
            saveHorario(medico, DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(17, 0));

            // Jueves
            saveHorario(medico, DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(14, 0));
            saveHorario(medico, DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0));

            // Viernes
            saveHorario(medico, DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));
            saveHorario(medico, DayOfWeek.FRIDAY, LocalTime.of(13, 0), LocalTime.of(17, 0));
        }

        log.info(">>> Horarios médicos inicializados correctamente.");
    }

    private void saveHorario(Medico medico, DayOfWeek dia, LocalTime inicio, LocalTime fin) {
        horarioMedicoRepository.save(
                HorarioMedico.builder()
                        .dia(dia)
                        .horaInicio(inicio)
                        .horaFin(fin)
                        .medico(medico)
                        .build()
        );
    }
}
