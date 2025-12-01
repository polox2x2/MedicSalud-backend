package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.Inventario;
import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;
import com.Proyecto.Medic.MedicSalud.Entity.Sede;
import com.Proyecto.Medic.MedicSalud.Repository.InventarioRepository;
import com.Proyecto.Medic.MedicSalud.Repository.MedicamentoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(11)
public class InventarioInitializer implements CommandLineRunner {

    private final InventarioRepository inventarioRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final SedeRepository sedeRepository;

    @Override
    public void run(String... args) {

        if (inventarioRepository.count() > 0) {
            log.info(">>> Inventario ya inicializado.");
            return;
        }

        List<Sede> sedes = sedeRepository.findAll();
        List<Medicamento> medicamentos = medicamentoRepository.findAll();

        if (sedes.isEmpty() || medicamentos.isEmpty()) {
            throw new IllegalStateException("No hay sedes o medicamentos para inicializar inventario.");
        }

        for (Sede sede : sedes) {
            for (Medicamento med : medicamentos) {

                Inventario inventario = Inventario.builder()
                        .sede(sede)
                        .medicamento(med)
                        .stock(100)    // stock inicial fijo
                        .estado(true)
                        .build();

                inventarioRepository.save(inventario);
            }
        }

        log.info(">>> Inventario inicial creado para todas las sedes y medicamentos.");
    }
}
