package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Entity.Sede;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
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
@Order(5)
public class MedicoInitializer implements CommandLineRunner {

    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SedeRepository sedeRepository;

    @Override
    public void run(String... args) {

        if (medicoRepository.count() > 0) {
            log.info(">>> Médicos ya inicializados.");
            return;
        }

        // Obtener usuario para médico (creado en UsuarioInitializer)
        Usuario usuarioMedico1 = usuarioRepository.findByEmail("medico1@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario medico1@medicsalud.com no encontrado"));

        Usuario usuarioMedico2 = usuarioRepository.findByEmail("lucia.guzman@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario lucia.guzman@medicsalud.com no encontrado"));

        Usuario usuarioMedico3 = usuarioRepository.findByEmail("roberto.caceres@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario roberto.caceres@medicsalud.com no encontrado"));

        Usuario usuarioMedico4 = usuarioRepository.findByEmail("esteban.villanueva@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario esteban.villanueva@medicsalud.com no encontrado"));

        Usuario usuarioMedico5 = usuarioRepository.findByEmail("patricia.lopez@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario patricia.lopez@medicsalud.com no encontrado"));

        Usuario usuarioMedico6 = usuarioRepository.findByEmail("fabian.huaman@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario fabian.huaman@medicsalud.com no encontrado"));



        Sede sedePrincipal = sedeRepository.findByNombreClinica("Hospital San Juan de Dios Pisco")
                .orElseThrow(() -> new IllegalStateException("Sede principal no encontrada"));


        medicoRepository.save(Medico.builder()
                .usuario(usuarioMedico1)
                .especialidad("Cardiología")
                .telefono(usuarioMedico1.getTelefono())
                .dni(usuarioMedico1.getDni())
                .estado(true)
                .sede(sedePrincipal)
                .build());

        medicoRepository.save(Medico.builder()
                .usuario(usuarioMedico2)
                .especialidad("Pediatría")
                .telefono(usuarioMedico2.getTelefono())
                .dni(usuarioMedico2.getDni())
                .estado(true)
                .sede(sedePrincipal)
                .build());

        medicoRepository.save(Medico.builder()
                .usuario(usuarioMedico3)
                .especialidad("Dermatología")
                .telefono(usuarioMedico3.getTelefono())
                .dni(usuarioMedico3.getDni())
                .estado(true)
                .sede(sedePrincipal)
                .build());

        medicoRepository.save(Medico.builder()
                .usuario(usuarioMedico4)
                .especialidad("Neurología")
                .telefono(usuarioMedico4.getTelefono())
                .dni(usuarioMedico4.getDni())
                .estado(true)
                .sede(sedePrincipal)
                .build());

        medicoRepository.save(Medico.builder()
                .usuario(usuarioMedico5)
                .especialidad("Ginecología")
                .telefono(usuarioMedico5.getTelefono())
                .dni(usuarioMedico5.getDni())
                .estado(true)
                .sede(sedePrincipal)
                .build());

        medicoRepository.save(Medico.builder()
                .usuario(usuarioMedico6)
                .especialidad("Traumatología")
                .telefono(usuarioMedico6.getTelefono())
                .dni(usuarioMedico6.getDni())
                .estado(true)
                .sede(sedePrincipal)
                .build());

        log.info(">>> Médicos inicializados correctamente.");
    }
}
