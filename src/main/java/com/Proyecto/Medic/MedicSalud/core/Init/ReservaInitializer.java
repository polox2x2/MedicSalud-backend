package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.*;
import com.Proyecto.Medic.MedicSalud.Repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(8)
@Transactional
public class ReservaInitializer implements CommandLineRunner {

    private final ReservaRepository reservaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final SedeRepository sedeRepository;

    @Override
    public void run(String... args) {

        if (reservaRepository.count() > 0) {
            log.info(">>> Reservas ya inicializadas.");
            return;
        }

        Medico medico = medicoRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay médicos inicializados"));

        Paciente paciente = pacienteRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay pacientes inicializados"));

        Sede sede = sedeRepository.findByNombreClinica("Hospital San Juan de Dios Pisco")
                .orElseThrow(() -> new IllegalStateException("Sede principal no encontrada"));

        LocalDate fechaCita = LocalDate.now().plusDays(3);  // 3 días adelante

        LocalTime horaCita = LocalTime.of(10, 0);

        Reserva reserva = Reserva.builder()
                .fechaCreacion(LocalDateTime.now())
                .fechaCita(fechaCita)
                .horaCita(horaCita)
                .estadoCita(true)
                .medico(medico)
                .paciente(paciente)
                .sede(sede)
                .build();

        reservaRepository.save(reserva);

        Reserva reserva2 = Reserva.builder()
                .fechaCreacion(LocalDateTime.now())
                .fechaCita(LocalDate.now().plusDays(5))
                .horaCita(LocalTime.of(16, 0))
                .estadoCita(true)
                .medico(medico)
                .paciente(paciente)
                .sede(sede)
                .build();

        reservaRepository.save(reserva2);

        log.info(">>> Reservas inicializadas correctamente.");
    }
}
