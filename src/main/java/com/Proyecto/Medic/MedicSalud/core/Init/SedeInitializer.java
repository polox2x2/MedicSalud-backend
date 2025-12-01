package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Sede;
import com.Proyecto.Medic.MedicSalud.Repository.SedeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(4)
public class SedeInitializer implements CommandLineRunner {

    private final SedeRepository sedeRepository;

    @Override
    public void run(String... args) {
        if (sedeRepository.count() > 0) {
            log.info(">>> Sedes ya inicializadas.");
            return;
        }

        sedeRepository.save(Sede.builder()
                .nombreClinica("Hospital San Juan de Dios Pisco")
                .direccion("C. San Clemente 234, Pisco 11601")
                .estado(true)
                .build());

        sedeRepository.save(Sede.builder()
                .nombreClinica("Clínica Central MedicSalud")
                .direccion("Av. Javier Prado 1234, San Isidro")
                .estado(true)
                .build());

        sedeRepository.save(Sede.builder()
                .nombreClinica("Clínica Norte MedicSalud")
                .direccion("Av. Universitaria 550, Los Olivos")
                .estado(true)
                .build());

        sedeRepository.save(Sede.builder()
                .nombreClinica("Clínica Sur MedicSalud")
                .direccion("Av. Tomás Marsano 2890, Surco")
                .estado(true)
                .build());

        sedeRepository.save(Sede.builder()
                .nombreClinica("Clínica Este MedicSalud")
                .direccion("Av. Metropolitana 402, Santa Anita")
                .estado(true)
                .build());

        log.info(">>> Sedes inicializadas correctamente.");
    }
}
