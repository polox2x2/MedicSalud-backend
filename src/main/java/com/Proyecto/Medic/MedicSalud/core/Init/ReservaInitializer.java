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
        private final HorarioMedicoRepository horarioMedicoRepository;

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

                LocalDate fechaCita = LocalDate.now().plusDays(3); // 3 días adelante

                LocalTime horaCita = LocalTime.of(10, 0);

                Reserva reserva = Reserva.builder()
                                .fechaCreacion(LocalDateTime.now())
                                .fechaCita(fechaCita)
                                .horaCita(horaCita)
                                .estadoCita(EstadoCita.PENDIENTE)
                                .medico(medico)
                                .paciente(paciente)
                                .sede(sede)
                                .horarioMedico(findHorario(medico, fechaCita, horaCita))
                                .build();

                reservaRepository.save(reserva);

                Reserva reserva2 = Reserva.builder()
                                .fechaCreacion(LocalDateTime.now())
                                .fechaCita(LocalDate.now().plusDays(5))
                                .horaCita(LocalTime.of(16, 0))
                                .estadoCita(EstadoCita.PENDIENTE)
                                .medico(medico)
                                .paciente(paciente)
                                .sede(sede)
                                .horarioMedico(findHorario(medico, LocalDate.now().plusDays(5), LocalTime.of(16, 0)))
                                .build();

                reservaRepository.save(reserva2);

                // Reserva CONFIRMADA (Ocupado)
                Reserva reserva3 = Reserva.builder()
                                .fechaCreacion(LocalDateTime.now())
                                .fechaCita(fechaCita)
                                .horaCita(LocalTime.of(11, 0))
                                .estadoCita(EstadoCita.CONFIRMADA)
                                .medico(medico)
                                .paciente(paciente)
                                .sede(sede)
                                .horarioMedico(findHorario(medico, fechaCita, LocalTime.of(11, 0)))
                                .build();
                reservaRepository.save(reserva3);

                Reserva reserva4 = Reserva.builder()
                                .fechaCreacion(LocalDateTime.now())
                                .fechaCita(fechaCita)
                                .horaCita(LocalTime.of(12, 0))
                                .estadoCita(EstadoCita.RECHAZADA)
                                .medico(medico)
                                .paciente(paciente)
                                .sede(sede)
                                .horarioMedico(findHorario(medico, fechaCita, LocalTime.of(12, 0)))
                                .build();
                reservaRepository.save(reserva4);

                Reserva reserva5 = Reserva.builder()
                                .fechaCreacion(LocalDateTime.now())
                                .fechaCita(fechaCita)
                                .horaCita(LocalTime.of(14, 0))
                                .estadoCita(EstadoCita.CONFIRMADA)
                                .medico(medico)
                                .paciente(paciente)
                                .sede(sede)
                                .horarioMedico(findHorario(medico, fechaCita, LocalTime.of(14, 0)))
                                .build();
                reservaRepository.save(reserva5);

                log.info(">>> Reservas inicializadas correctamente.");
        }

        private com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico findHorario(
                        com.Proyecto.Medic.MedicSalud.Entity.Medico medico, java.time.LocalDate fecha,
                        java.time.LocalTime hora) {
                return horarioMedicoRepository.findByMedicoAndFecha(medico, fecha)
                                .stream()
                                .filter(h -> !hora.isBefore(h.getHoraInicio()) && hora.isBefore(h.getHoraFin()))
                                .findFirst()
                                .orElse(null);
        }
}
