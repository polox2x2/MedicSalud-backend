package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class RoleInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;



    @Override
    public void run(String... args) {
        if (rolRepository.count() > 0) {
            log.info(">>> Roles ya inicializados.");
            return;
        }

        rolRepository.save(Rol.builder().nombre("ADMIN").build());
        rolRepository.save(Rol.builder().nombre("MEDICO").build());
        rolRepository.save(Rol.builder().nombre("PACIENTE").build());
        rolRepository.save(Rol.builder().nombre("VENDEDOR").build());

        log.info(">>> Roles inicializados correctamente.");
    }
}
