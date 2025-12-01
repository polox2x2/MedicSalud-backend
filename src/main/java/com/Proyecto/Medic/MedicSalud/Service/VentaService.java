package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.Venta.CrearVentaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Venta.VentaItemDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Venta.VentaResponseDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Venta.DetalleVentaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.*;
import com.Proyecto.Medic.MedicSalud.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final InventarioRepository inventarioRepository;
    private final PacienteRepository pacienteRepository;
    private final SedeRepository sedeRepository;

    @Transactional
    public VentaResponseDTO crearVenta(CrearVentaDTO request) {

        Sede sede = sedeRepository.findById(request.getSedeId())
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));

        Paciente paciente = pacienteRepository
                .findByDniAndEstadoTrue(request.getPacienteDni())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setSede(sede);
        venta.setPaciente(paciente);
        venta.setEstado(true);

        BigDecimal total = BigDecimal.ZERO;

        for (VentaItemDTO item : request.getItems()) {

            Medicamento medicamento = medicamentoRepository
                    .findByIdAndEstadoTrue(item.getMedicamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado"));

            Inventario inventario = inventarioRepository
                    .findByMedicamento_IdAndSede_IdAndEstadoTrue(medicamento.getId(), sede.getId())
                    .orElseThrow(() -> new IllegalStateException(
                            "No existe inventario para el medicamento " +
                                    medicamento.getNombre() + " en la sede " + sede.getId()
                    ));

            if (inventario.getStock() < item.getCantidad()) {
                throw new IllegalStateException(
                        "Stock insuficiente para " + medicamento.getNombre() +
                                " (stock actual: " + inventario.getStock() + ")"
                );
            }


            inventario.setStock(inventario.getStock() - item.getCantidad());
            if (inventario.getStock() == 0) {
                inventario.setEstado(false);
            }

            BigDecimal precioUnitario = medicamento.getPrecioVenta();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad()));

            DetalleVenta detalle = DetalleVenta.builder()
                    .medicamento(medicamento)
                    .cantidad(item.getCantidad())
                    .precioUnitario(precioUnitario)
                    .subtotal(subtotal)
                    .build();

            venta.addDetalle(detalle);
            total = total.add(subtotal);
        }

        venta.setTotal(total);

        Venta guardada = ventaRepository.save(venta);

        return mapToResponseDTO(guardada);
    }

    private VentaResponseDTO mapToResponseDTO(Venta venta) {
        return VentaResponseDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .sedeId(venta.getSede().getId())
                .sedeNombre(venta.getSede().getNombreClinica())
                .pacienteId(venta.getPaciente().getId())
                .pacienteNombre(venta.getPaciente().getUsuario().getNombre())
                .pacienteDni(venta.getPaciente().getDni())
                .total(venta.getTotal())
                .items(
                        venta.getDetalles().stream()
                                .map(d -> DetalleVentaResponseDTO.builder()
                                        .medicamentoId(d.getMedicamento().getId())
                                        .medicamentoNombre(d.getMedicamento().getNombre())
                                        .cantidad(d.getCantidad())
                                        .precioUnitario(d.getPrecioUnitario())
                                        .subtotal(d.getSubtotal())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }
}
