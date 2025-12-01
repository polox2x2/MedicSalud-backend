package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;
import com.Proyecto.Medic.MedicSalud.Repository.MedicamentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class MedicamentoInitializer implements CommandLineRunner {

    private final MedicamentoRepository medicamentoRepository;

    @Override
    public void run(String... args) {
        if (medicamentoRepository.count() > 0) {
            log.info(">>> Medicamentos ya inicializados.");
            return;
        }

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Ibuprofeno 400 mg")
                .descripcion("Antiinflamatorio y analgésico")
                .precioVenta(new BigDecimal("12.50"))
                .codigoBarras("IBU400A1")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Paracetamol 500 mg")
                .descripcion("Analgésico y antipirético")
                .precioVenta(new BigDecimal("9.00"))
                .codigoBarras("PARA500B2")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Amoxicilina 500 mg")
                .descripcion("Antibiótico penicilánico")
                .precioVenta(new BigDecimal("18.90"))
                .codigoBarras("AMOX500C3")
                .requiereReceta(true)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Omeprazol 20 mg")
                .descripcion("Protector gástrico")
                .precioVenta(new BigDecimal("15.50"))
                .codigoBarras("OME20D4")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Losartán 50 mg")
                .descripcion("Antihipertensivo")
                .precioVenta(new BigDecimal("20.00"))
                .codigoBarras("LOS50E5")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Azitromicina 500 mg")
                .descripcion("Antibiótico de amplio espectro")
                .precioVenta(new BigDecimal("35.00"))
                .codigoBarras("AZI500F6")
                .requiereReceta(true)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Metformina 850 mg")
                .descripcion("Tratamiento para diabetes tipo 2")
                .precioVenta(new BigDecimal("17.80"))
                .codigoBarras("MET850G7")
                .requiereReceta(false)
                .estado(true)
                .build());


        medicamentoRepository.save(Medicamento.builder()
                .nombre("Aspirina 100 mg")
                .descripcion("Analgésico y antiplaquetario")
                .precioVenta(new BigDecimal("8.50"))
                .codigoBarras("ASP100H1")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Diclofenaco 50 mg")
                .descripcion("Antiinflamatorio y analgésico")
                .precioVenta(new BigDecimal("14.00"))
                .codigoBarras("DIC50H2")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Clorfenamina 4 mg")
                .descripcion("Antialérgico antihistamínico")
                .precioVenta(new BigDecimal("6.80"))
                .codigoBarras("CLOR4H3")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Captopril 25 mg")
                .descripcion("Antihipertensivo inhibidor de la ECA")
                .precioVenta(new BigDecimal("11.30"))
                .codigoBarras("CAP25H4")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Atorvastatina 20 mg")
                .descripcion("Reductor de colesterol LDL")
                .precioVenta(new BigDecimal("28.50"))
                .codigoBarras("ATOR20H5")
                .requiereReceta(true)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Insulina Glargina 100 UI")
                .descripcion("Tratamiento para diabetes mellitus tipo 1 y 2")
                .precioVenta(new BigDecimal("120.00"))
                .codigoBarras("INS100H6")
                .requiereReceta(true)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Salbutamol Inhalador 100 mcg")
                .descripcion("Broncodilatador para crisis asmáticas")
                .precioVenta(new BigDecimal("24.90"))
                .codigoBarras("SALB100H7")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Vitamina C 1 g")
                .descripcion("Suplemento antioxidante")
                .precioVenta(new BigDecimal("5.50"))
                .codigoBarras("VITC1H8")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Hierro Ferroso 325 mg")
                .descripcion("Tratamiento para anemia ferropénica")
                .precioVenta(new BigDecimal("16.40"))
                .codigoBarras("HIER325H9")
                .requiereReceta(false)
                .estado(true)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nombre("Prednisona 20 mg")
                .descripcion("Corticosteroide sistémico para alergias y inflamación")
                .precioVenta(new BigDecimal("12.00"))
                .codigoBarras("PRED20H10")
                .requiereReceta(true)
                .estado(true)
                .build());


        log.info(">>> Medicamentos inicializados correctamente.");
    }
}
