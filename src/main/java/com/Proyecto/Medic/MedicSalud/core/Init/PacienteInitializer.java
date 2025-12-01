package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
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
@Order(7)
public class PacienteInitializer implements CommandLineRunner {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {

        if (pacienteRepository.count() > 0) {
            log.info(">>> Pacientes ya inicializados.");
            return;
        }

        Usuario uAnthony = usuarioRepository.findByEmail("gjkakahjk135@gmail.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Anthony no encontrado"));

        Usuario uMaria = usuarioRepository.findByEmail("maria.lozano@gmail.com")
                .orElseThrow(() -> new IllegalStateException("Usuario María no encontrado"));

        Usuario uJorge = usuarioRepository.findByEmail("jorge.ramirez@hotmail.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Jorge no encontrado"));

        Usuario uDaniel = usuarioRepository.findByEmail("daniel.torres@gmail.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Daniel no encontrado"));

        Usuario uValeria = usuarioRepository.findByEmail("valeria.chavez@gmail.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Valeria no encontrado"));

        Usuario uRenzo = usuarioRepository.findByEmail("renzo.calderon@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Renzo no encontrado"));

        Usuario uGabriela = usuarioRepository.findByEmail("gabriela.mendoza@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Gabriela no encontrado"));

        Usuario uJulio = usuarioRepository.findByEmail("julio.sandoval@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Julio no encontrado"));

        Usuario uNatalia = usuarioRepository.findByEmail("natalia.bustamante@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Natalia no encontrado"));

        Usuario uLorena = usuarioRepository.findByEmail("lorena.garcia@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Lorena no encontrado"));

        Usuario uMichelle = usuarioRepository.findByEmail("michelle.torres@medicsalud.com")
                .orElseThrow(() -> new IllegalStateException("Usuario Michelle no encontrado"));

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uAnthony)
                        .nombreUsuario(uAnthony.getNombre() + " " + uAnthony.getApellido())
                        .dni(uAnthony.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uMaria)
                        .nombreUsuario(uMaria.getNombre() + " " + uMaria.getApellido())
                        .dni(uMaria.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uJorge)
                        .nombreUsuario(uJorge.getNombre() + " " + uJorge.getApellido())
                        .dni(uJorge.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uDaniel)
                        .nombreUsuario(uDaniel.getNombre() + " " + uDaniel.getApellido())
                        .dni(uDaniel.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uValeria)
                        .nombreUsuario(uValeria.getNombre() + " " + uValeria.getApellido())
                        .dni(uValeria.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uRenzo)
                        .nombreUsuario(uRenzo.getNombre() + " " + uRenzo.getApellido())
                        .dni(uRenzo.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uGabriela)
                        .nombreUsuario(uGabriela.getNombre() + " " + uGabriela.getApellido())
                        .dni(uGabriela.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uJulio)
                        .nombreUsuario(uJulio.getNombre() + " " + uJulio.getApellido())
                        .dni(uJulio.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uNatalia)
                        .nombreUsuario(uNatalia.getNombre() + " " + uNatalia.getApellido())
                        .dni(uNatalia.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uLorena)
                        .nombreUsuario(uLorena.getNombre() + " " + uLorena.getApellido())
                        .dni(uLorena.getDni())
                        .estado(true)
                        .build()
        );

        pacienteRepository.save(
                Paciente.builder()
                        .usuario(uMichelle)
                        .nombreUsuario(uMichelle.getNombre() + " " + uMichelle.getApellido())
                        .dni(uMichelle.getDni())
                        .estado(true)
                        .build()
        );

        log.info(">>> Pacientes inicializados correctamente.");
    }
}
