package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Repository.RolRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class UserInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (usuarioRepository.count() > 0) {
            log.info(">>> Usuarios ya inicializados.");
            return;
        }

        Rol rolAdmin = rolRepository.findByNombre("ADMIN")
                .orElseThrow(() -> new IllegalStateException("Rol ADMIN no encontrado"));
        Rol rolMedico = rolRepository.findByNombre("MEDICO")
                .orElseThrow(() -> new IllegalStateException("Rol MEDICO no encontrado"));
        Rol rolPaciente = rolRepository.findByNombre("PACIENTE")
                .orElseThrow(() -> new IllegalStateException("Rol PACIENTE no encontrado"));
        Rol rolVendedor = rolRepository.findByNombre("VENDEDOR")
                .orElseThrow(() -> new IllegalStateException("Rol VENDEDOR no encontrado"));


        Usuario admin = Usuario.builder()
                .nombre("Administrador")
                .apellido("General")
                .dni(11111111)
                .email("admin@medicsalud.com")
                .clave(passwordEncoder.encode("Admin123$"))
                .fechaNacimiento(LocalDate.of(1980, 1, 1))
                .telefono("900000000")
                .direccion("Av. Principal 100")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolAdmin))
                .build();

        usuarioRepository.save(admin);



        Usuario medico = Usuario.builder()
                .nombre("Ana")
                .apellido("Fernández López")
                .dni(45678912)
                .email("medico1@medicsalud.com")
                .clave(passwordEncoder.encode("Medico123$"))
                .fechaNacimiento(LocalDate.of(1988, 3, 10))
                .telefono("988776655")
                .direccion("Av. Salud 321")
                .especialidad("Cardiología")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolMedico))
                .build();

        usuarioRepository.save(medico);



        Usuario paciente = Usuario.builder()
                .nombre("Anthony")
                .apellido("Mencias Kazaski")
                .dni(72376198)
                .email("gjkakahjk135@gmail.com")
                .clave(passwordEncoder.encode("claveSegura123"))
                .fechaNacimiento(LocalDate.of(1999, 5, 12))
                .telefono("987653324")
                .direccion("Av. Tacna 456")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();

        usuarioRepository.save(paciente);



        Usuario vendedor = Usuario.builder()
                .nombre("Carlos")
                .apellido("Ventas")
                .dni(88776655)
                .email("vendedor@medicsalud.com")
                .clave(passwordEncoder.encode("Vendedor123$"))
                .fechaNacimiento(LocalDate.of(1995, 7, 20))
                .telefono("955443322")
                .direccion("Av. Comercio 999")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolVendedor))
                .build();

        usuarioRepository.save(vendedor);





        Usuario paciente2 = Usuario.builder()
                .nombre("María")
                .apellido("Lozano Pérez")
                .dni(72639184)
                .email("maria.lozano@gmail.com")
                .clave(passwordEncoder.encode("Maria123$"))
                .fechaNacimiento(LocalDate.of(2001, 8, 22))
                .telefono("987654123")
                .direccion("Calle Los Cedros 120")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(paciente2);



        Usuario paciente3 = Usuario.builder()
                .nombre("Jorge")
                .apellido("Ramírez Soto")
                .dni(71592834)
                .email("jorge.ramirez@hotmail.com")
                .clave(passwordEncoder.encode("Jorge321$"))
                .fechaNacimiento(LocalDate.of(1994, 11, 10))
                .telefono("945123789")
                .direccion("Av. Progreso 450")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(paciente3);



        Usuario medico2 = Usuario.builder()
                .nombre("Lucía")
                .apellido("Guzmán Herrera")
                .dni(74821935)
                .email("lucia.guzman@medicsalud.com")
                .clave(passwordEncoder.encode("LuciaMed123$"))
                .fechaNacimiento(LocalDate.of(1987, 4, 5))
                .telefono("912345678")
                .direccion("Av. Los Médicos 312")
                .especialidad("Pediatría")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolMedico))
                .build();
        usuarioRepository.save(medico2);



        Usuario medico3 = Usuario.builder()
                .nombre("Roberto")
                .apellido("Cáceres Molina")
                .dni(75839216)
                .email("roberto.caceres@medicsalud.com")
                .clave(passwordEncoder.encode("RobMed123$"))
                .fechaNacimiento(LocalDate.of(1983, 6, 14))
                .telefono("934512678")
                .direccion("Jr. Medicina 445")
                .especialidad("Dermatología")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolMedico))
                .build();
        usuarioRepository.save(medico3);



        Usuario vendedor2 = Usuario.builder()
                .nombre("Sofía")
                .apellido("Quispe Ramos")
                .dni(73245198)
                .email("sofia.vendedora@medicsalud.com")
                .clave(passwordEncoder.encode("SofiaVen123$"))
                .fechaNacimiento(LocalDate.of(1997, 2, 18))
                .telefono("987987321")
                .direccion("Mercado Central 1200")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolVendedor))
                .build();
        usuarioRepository.save(vendedor2);



        Usuario paciente4 = Usuario.builder()
                .nombre("Daniel")
                .apellido("Torres Aguilar")
                .dni(71928346)
                .email("daniel.torres@gmail.com")
                .clave(passwordEncoder.encode("DanielT123$"))
                .fechaNacimiento(LocalDate.of(1996, 3, 25))
                .telefono("923456789")
                .direccion("Pasaje Los Jazmines 88")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(paciente4);



        Usuario medico4 = Usuario.builder()
                .nombre("Esteban")
                .apellido("Villanueva Prado")
                .dni(75283916)
                .email("esteban.villanueva@medicsalud.com")
                .clave(passwordEncoder.encode("EstebanMed123$"))
                .fechaNacimiento(LocalDate.of(1984, 7, 3))
                .telefono("943678512")
                .direccion("Residencial Salud 220")
                .especialidad("Neurología")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolMedico))
                .build();
        usuarioRepository.save(medico4);



        Usuario paciente5 = Usuario.builder()
                .nombre("Valeria")
                .apellido("Chávez Oré")
                .dni(73649281)
                .email("valeria.chavez@gmail.com")
                .clave(passwordEncoder.encode("Vale12345$"))
                .fechaNacimiento(LocalDate.of(2000, 12, 2))
                .telefono("967234981")
                .direccion("Calle Primavera 133")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(paciente5);



        Usuario medico5 = Usuario.builder()
                .nombre("Patricia")
                .apellido("López Santamaría")
                .dni(76123985)
                .email("patricia.lopez@medicsalud.com")
                .clave(passwordEncoder.encode("PatMed123$"))
                .fechaNacimiento(LocalDate.of(1990, 1, 15))
                .telefono("921567843")
                .direccion("Av. Universitaria 560")
                .especialidad("Ginecología")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolMedico))
                .build();
        usuarioRepository.save(medico5);



        Usuario vendedor3 = Usuario.builder()
                .nombre("Hernán")
                .apellido("Córdova Bendezú")
                .dni(74561293)
                .email("hernan.cordova@medicsalud.com")
                .clave(passwordEncoder.encode("HerVen123$"))
                .fechaNacimiento(LocalDate.of(1992, 9, 29))
                .telefono("954321678")
                .direccion("Av. Gran Comercio 200")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolVendedor))
                .build();
        usuarioRepository.save(vendedor3);

        Usuario u14 = Usuario.builder()
                .nombre("Renzo")
                .apellido("Calderón Muñoz")
                .dni(74629183)
                .email("renzo.calderon@medicsalud.com")
                .clave(passwordEncoder.encode("Renzo123$"))
                .fechaNacimiento(LocalDate.of(1993, 5, 19))
                .telefono("987456321")
                .direccion("Av. Grau 540")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(u14);

        Usuario u15 = Usuario.builder()
                .nombre("Gabriela")
                .apellido("Mendoza Silva")
                .dni(75483129)
                .email("gabriela.mendoza@medicsalud.com")
                .clave(passwordEncoder.encode("Gaby123$"))
                .fechaNacimiento(LocalDate.of(1997, 7, 14))
                .telefono("934872561")
                .direccion("Jr. Libertad 129")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(u15);

        Usuario u16 = Usuario.builder()
                .nombre("Fabián")
                .apellido("Huamán Rivera")
                .dni(73198264)
                .email("fabian.huaman@medicsalud.com")
                .clave(passwordEncoder.encode("Fabian123$"))
                .fechaNacimiento(LocalDate.of(1991, 10, 30))
                .telefono("945612873")
                .direccion("Av. Miraflores 900")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolMedico))
                .especialidad("Traumatología")
                .build();
        usuarioRepository.save(u16);

        Usuario u17 = Usuario.builder()
                .nombre("Carolina")
                .apellido("Ríos Palomino")
                .dni(76349215)
                .email("carolina.rios@medicsalud.com")
                .clave(passwordEncoder.encode("Caro123$"))
                .fechaNacimiento(LocalDate.of(1989, 12, 21))
                .telefono("923498176")
                .direccion("Residencial El Sol 220")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolMedico))
                .especialidad("Oftalmología")
                .build();
        usuarioRepository.save(u17);

        Usuario u18 = Usuario.builder()
                .nombre("Julio")
                .apellido("Sandoval Torres")
                .dni(72831954)
                .email("julio.sandoval@medicsalud.com")
                .clave(passwordEncoder.encode("Julio123$"))
                .fechaNacimiento(LocalDate.of(1995, 4, 11))
                .telefono("900765432")
                .direccion("Calle San Juan 78")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(u18);

        Usuario u19 = Usuario.builder()
                .nombre("Natalia")
                .apellido("Bustamante Vega")
                .dni(73659284)
                .email("natalia.bustamante@medicsalud.com")
                .clave(passwordEncoder.encode("Naty123$"))
                .fechaNacimiento(LocalDate.of(1998, 3, 16))
                .telefono("978231564")
                .direccion("Av. Los Jazmines 312")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(u19);

        Usuario u20 = Usuario.builder()
                .nombre("Héctor")
                .apellido("Paredes Castañeda")
                .dni(74921386)
                .email("hector.paredes@medicsalud.com")
                .clave(passwordEncoder.encode("Hector123$"))
                .fechaNacimiento(LocalDate.of(1986, 2, 7))
                .telefono("965478321")
                .direccion("Av. Metropolitana 456")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolVendedor))
                .build();
        usuarioRepository.save(u20);

        Usuario u21 = Usuario.builder()
                .nombre("Lorena")
                .apellido("García Huertas")
                .dni(71298463)
                .email("lorena.garcia@medicsalud.com")
                .clave(passwordEncoder.encode("Lore123$"))
                .fechaNacimiento(LocalDate.of(1994, 9, 3))
                .telefono("987345219")
                .direccion("Calle Los Tulipanes 455")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(u21);

        Usuario u22 = Usuario.builder()
                .nombre("Alonso")
                .apellido("Vargas Quintana")
                .dni(75932184)
                .email("alonso.vargas@medicsalud.com")
                .clave(passwordEncoder.encode("Alonso123$"))
                .fechaNacimiento(LocalDate.of(1990, 6, 28))
                .telefono("912783456")
                .direccion("Urbanización Santa Ana 102")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolMedico))
                .especialidad("Otorrinolaringología")
                .build();
        usuarioRepository.save(u22);

        Usuario u23 = Usuario.builder()
                .nombre("Michelle")
                .apellido("Torres Valdivia")
                .dni(72619483)
                .email("michelle.torres@medicsalud.com")
                .clave(passwordEncoder.encode("Michelle123$"))
                .fechaNacimiento(LocalDate.of(1999, 11, 6))
                .telefono("954671283")
                .direccion("Jr. Los Almendros 230")
                .fechaCreacion(LocalDateTime.now())
                .estado(true)
                .roles(Set.of(rolPaciente))
                .build();
        usuarioRepository.save(u23);



        log.info(">>> Usuarios creados correctamente con roles y datos completos.");
    }






}