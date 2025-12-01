package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Receta;
import com.Proyecto.Medic.MedicSalud.Repository.MedicamentoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.RecetaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(9)
@Transactional
public class RecetaInitializer implements CommandLineRunner {

    private final RecetaRepository recetaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final MedicamentoRepository medicamentoRepository;

    @Override
    public void run(String... args) {

        if (recetaRepository.count() > 0) {
            log.info(">>> Recetas ya inicializadas.");
            return;
        }

        Paciente paciente = pacienteRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay pacientes para recetas"));

        Medico medico = medicoRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay médicos para recetas"));

        Medicamento medicamento1 = medicamentoRepository.findByNombre("Ibuprofeno 400 mg")
                .orElseGet(() -> medicamentoRepository.findAll()
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No hay medicamentos para recetas")));

        Medicamento medicamento2 = medicamentoRepository.findByNombre("Paracetamol 500 mg")
                .orElseGet(() -> medicamentoRepository.findAll()
                        .stream()
                        .skip(1)
                        .findFirst()
                        .orElse(medicamento1));

        Receta r1 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(medicamento1)
                .dosis("1 tableta cada 8 horas por 5 días")
                .indicaciones("Tomar después de los alimentos. No conducir si presenta somnolencia.")
                .fechaCreacion(LocalDateTime.now())
                .build();

        Receta r2 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(medicamento2)
                .dosis("1 tableta cada 6 horas si hay dolor o fiebre")
                .indicaciones("No exceder 4 gramos al día. Suspender si hay malestar gástrico intenso.")
                .fechaCreacion(LocalDateTime.now())
                .build();

        recetaRepository.save(r1);
        recetaRepository.save(r2);


        Receta r3 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Amoxicilina 500 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 cápsula cada 8 horas por 7 días")
                .indicaciones("Tomar con agua. Completar todo el tratamiento aunque desaparezcan los síntomas.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r3);

        Receta r4 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Omeprazol 20 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 cápsula diaria 30 minutos antes del desayuno")
                .indicaciones("Evitar comidas irritantes y bebidas alcohólicas.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r4);

        Receta r5 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Losartán 50 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 tableta al día")
                .indicaciones("Medir presión arterial diariamente. No suspender sin indicación médica.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r5);

        Receta r6 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Azitromicina 500 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 tableta diaria por 3 días")
                .indicaciones("No combinar con antiácidos. Consumir abundante agua.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r6);

        Receta r7 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Metformina 850 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 tableta con el almuerzo y cena")
                .indicaciones("Evitar alcohol. Controlar niveles de glucosa frecuentemente.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r7);

        Receta r8 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Aspirina 100 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 tableta diaria")
                .indicaciones("Tomar con alimentos. Suspender si ocurre sangrado.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r8);

        Receta r9 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Diclofenaco 50 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 tableta cada 12 horas")
                .indicaciones("No usar más de 5 días seguidos. Riesgo de irritación gástrica.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r9);

        Receta r10 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Clorfenamina 4 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 tableta cada 8 horas")
                .indicaciones("Puede causar somnolencia. Evitar conducir vehículos.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r10);

        Receta r11 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Captopril 25 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 tableta cada 12 horas")
                .indicaciones("Tomar 1 hora antes de los alimentos. Controlar presión.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r11);

        Receta r12 = Receta.builder()
                .paciente(paciente)
                .medico(medico)
                .medicamento(
                        medicamentoRepository.findByNombre("Atorvastatina 20 mg")
                                .orElseThrow(() -> new IllegalStateException("Falta medicamento"))
                )
                .dosis("1 tableta antes de dormir")
                .indicaciones("Mantener dieta baja en grasas. No mezclar con alcohol.")
                .fechaCreacion(LocalDateTime.now())
                .build();
        recetaRepository.save(r12);


        log.info(">>> Recetas inicializadas correctamente.");
    }
}
