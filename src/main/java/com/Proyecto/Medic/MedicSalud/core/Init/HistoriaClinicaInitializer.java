package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Historial;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Repository.HistorialRepository;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;

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
@Order(10)
public class HistoriaClinicaInitializer implements CommandLineRunner {

    private final HistorialRepository historialRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Override
    public void run(String... args) {

        if (historialRepository.count() > 0) {
            log.info(">>> Historiales médicos ya inicializados.");
            return;
        }

        Paciente paciente = pacienteRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay pacientes para historiales"));

        Medico medico = medicoRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay médicos para historiales"));

        Historial h1 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Cuadro de infección respiratoria leve")
                .tratamiento("Azitromicina 500 mg por 3 días, reposo y buena hidratación")
                .observaciones("Se recomienda seguimiento en 5 días. Paciente sin signos de alarma.")
                .tipoSangre("O+")
                .build();

        Historial h2 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Gastritis aguda")
                .tratamiento("Omeprazol 20 mg por 14 días, dieta blanda y evitar irritantes")
                .observaciones("Se sugiere control si persisten molestias.")
                .tipoSangre("O+")
                .build();

        Historial h3 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Hipertensión arterial leve")
                .tratamiento("Losartán 50 mg 1 vez al día, reducción de sal y control de peso")
                .observaciones("Tomar presión diariamente por 2 semanas.")
                .tipoSangre("O+")
                .build();

        historialRepository.save(h1);
        historialRepository.save(h2);
        historialRepository.save(h3);

        Historial h4 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Migraña recurrente")
                .tratamiento("Paracetamol 500 mg cada 8 horas, reposo en ambientes sin ruido")
                .observaciones("Se recomienda evitar pantallas brillantes y estrés por 48 horas")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h4);

        Historial h5 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Alergia estacional moderada")
                .tratamiento("Clorfenamina 4 mg cada 12 horas")
                .observaciones("Síntomas aumentan en primavera; evitar contacto con polvo y plantas")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h5);

        Historial h6 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Anemia leve por deficiencia de hierro")
                .tratamiento("Hierro ferroso 325 mg una vez al día por 3 meses")
                .observaciones("Revisar hemoglobina en 90 días")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h6);

        Historial h7 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Lumbalgia aguda")
                .tratamiento("Diclofenaco 50 mg cada 8 horas por 5 días, reposo y fisioterapia")
                .observaciones("Evitar cargar peso y mantener buena postura")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h7);

        Historial h8 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Infección urinaria leve")
                .tratamiento("Amoxicilina 500 mg cada 8 horas por 7 días, hidratación abundante")
                .observaciones("Realizar examen de orina si reaparecen síntomas")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h8);

        Historial h9 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Rinitis alérgica persistente")
                .tratamiento("Antihistamínicos diarios, limpieza nasal con solución salina")
                .observaciones("Reevaluación en 30 días; posible prueba de alergias")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h9);

        Historial h10 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Tendinitis del manguito rotador")
                .tratamiento("Antiinflamatorios y fisioterapia por 4 semanas")
                .observaciones("Evitar movimientos bruscos del hombro afectado")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h10);

        Historial h11 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Sinusitis aguda bacteriana")
                .tratamiento("Amoxicilina – Clavulánico por 10 días, vaporaciones y descanso")
                .observaciones("Control si no mejora en 72 horas")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h11);

        Historial h12 = Historial.builder()
                .paciente(paciente)
                .medico(medico)
                .diagnostico("Dermatitis de contacto")
                .tratamiento("Crema hidratante, evitar perfumes y jabones irritantes")
                .observaciones("Posible alergia a cosméticos; pruebas recomendadas")
                .tipoSangre("O+")
                .build();
        historialRepository.save(h12);


        log.info(">>> Historial médico inicializado correctamente.");
    }
}
