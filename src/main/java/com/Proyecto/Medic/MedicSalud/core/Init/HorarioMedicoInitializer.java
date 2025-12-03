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

        java.time.LocalDate today = java.time.LocalDate.now();

        for (Medico medico : medicos) {

            for (int i = 0; i < 7; i++) {
                java.time.LocalDate fecha = today.plusDays(i);


                saveHorario(medico, fecha, LocalTime.of(8, 0), LocalTime.of(13, 0));


                saveHorario(medico, fecha, LocalTime.of(14, 0), LocalTime.of(18, 0));
            }
        }

        log.info(">>> Horarios médicos inicializados correctamente.");
    }

    private void saveHorario(Medico medico, java.time.LocalDate fecha, LocalTime inicio, LocalTime fin) {
        horarioMedicoRepository.save(
                HorarioMedico.builder()
                        .fecha(fecha)
                        .horaInicio(inicio)
                        .horaFin(fin)
                        .medico(medico)
                        .activo(true)
                        .build());
    }
}
