package com.Proyecto.Medic.MedicSalud.core.Init;

import com.Proyecto.Medic.MedicSalud.Entity.*;
import com.Proyecto.Medic.MedicSalud.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
@Order(12)
public class VentaInitializer implements CommandLineRunner {

    private final VentaRepository ventaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final InventarioRepository inventarioRepository;
    private final PacienteRepository pacienteRepository;
    private final SedeRepository sedeRepository;

    @Override
    public void run(String... args) {

        // si ya hay ventas, no volver a sembrar
        if (ventaRepository.count() > 0) {
            log.info(">>> Ventas ya inicializadas.");
            return;
        }

        List<Sede> sedes = sedeRepository.findAll();
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<Medicamento> medicamentos = medicamentoRepository.findAll();

        if (sedes.isEmpty() || pacientes.isEmpty() || medicamentos.isEmpty()) {
            throw new IllegalStateException("Faltan sedes, pacientes o medicamentos para inicializar ventas.");
        }

        Random random = new Random();

        // Generar 10 ventas
        for (int i = 0; i < 10; i++) {

            Sede sede = sedes.get(random.nextInt(sedes.size()));
            Paciente paciente = pacientes.get(random.nextInt(pacientes.size()));

            Venta venta = Venta.builder()
                    .fecha(LocalDateTime.now().minusDays(10 - i))
                    .sede(sede)
                    .paciente(paciente)
                    .estado(true)
                    .build();

            BigDecimal total = BigDecimal.ZERO;

            int items = 1 + random.nextInt(4);

            // Controlar medicamentos ya usados en ESTA venta
            Set<Long> medicamentosUsados = new HashSet<>();

            for (int j = 0; j < items; j++) {

                Medicamento med = medicamentos.get(random.nextInt(medicamentos.size()));

                // si ya se usó en esta venta, buscar otro
                if (medicamentosUsados.contains(med.getId())) {
                    j--;              // reintentar este item
                    continue;
                }

                Inventario inventario = inventarioRepository
                        .findByMedicamento_IdAndSede_IdAndEstadoTrue(med.getId(), sede.getId())
                        .orElse(null);

                if (inventario == null || inventario.getStock() <= 0) {
                    j--;              // reintentar con otro medicamento
                    continue;
                }

                int maxCantidad = Math.min(5, inventario.getStock());
                int cantidad = 1 + random.nextInt(maxCantidad);

                BigDecimal precioUnitario = med.getPrecioVenta();
                BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

                DetalleVenta detalle = DetalleVenta.builder()
                        .medicamento(med)
                        .cantidad(cantidad)
                        .precioUnitario(precioUnitario)
                        .subtotal(subtotal)
                        .build();

                venta.addDetalle(detalle);
                total = total.add(subtotal);

                // marcar medicamento usado
                medicamentosUsados.add(med.getId());

                inventario.setStock(inventario.getStock() - cantidad);
                if (inventario.getStock() == 0) {
                    inventario.setEstado(false);
                }
                inventarioRepository.save(inventario);
            }

            if (venta.getDetalles().isEmpty()) {
                continue;
            }

            venta.setTotal(total);

            if (i == 8 || i == 9) {
                venta.setEstado(false);
            }

            ventaRepository.save(venta);
        }

        log.info(">>> Ventas y detalles de venta inicializadas correctamente (10 ventas creadas).");
    }
}
