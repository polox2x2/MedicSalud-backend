package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.*;
import com.Proyecto.Medic.MedicSalud.Entity.*;
import com.Proyecto.Medic.MedicSalud.Mappers.Inventario.DetalleVentaMapper;
import com.Proyecto.Medic.MedicSalud.Mappers.Inventario.VentaMapper;
import com.Proyecto.Medic.MedicSalud.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepo;
    private final DetalleVentaRepository detalleVentaRepository;
    private final InventarioRepository inventarioRepo;
    private final MedicamentoRepository medicamentoRepo;
    private final SedeRepository sedeRepository;
    private final PacienteRepository pacienteRepository;


    @Transactional
    public VentaResponseDTO crearVenta(VentaRequestDTO dto) {

        var sede = sedeRepository.findById(dto.getSedeId())
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));

        Paciente paciente = null;
        if (dto.getPacienteId() != null) {
            paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        }

        var venta = Venta.builder()
                .sede(sede)
                .paciente(paciente)
                .total(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (VentaItemDTO item : dto.getItems()) {
            var med = medicamentoRepo.findById(item.getMedicamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado: " + item.getMedicamentoId()));

            var inv = inventarioRepo.findBySedeIdAndMedicamentoId(sede.getId(), med.getId())
                    .orElseThrow(() -> new IllegalStateException("Inventario no disponible en esta sede"));

            if (inv.getStock() < item.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente de " + med.getNombre());
            }

            inv.setStock(inv.getStock() - item.getCantidad());
            // inventarioRepo.save(inv); // opcional; con @Transactional y entidad gestionada, no es necesario

            DetalleVenta det = DetalleVentaMapper.fromItem(item, venta, med);
            venta.getDetalles().add(det);
            total = total.add(det.getSubtotal());
        }

        venta.setTotal(total);
        var guardada = ventaRepo.save(venta); // cascade guarda detalles

        return VentaMapper.toResponse(guardada);
    }

}
