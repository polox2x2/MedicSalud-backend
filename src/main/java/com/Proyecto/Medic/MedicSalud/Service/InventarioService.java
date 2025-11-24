package com.Proyecto.Medic.MedicSalud.Service;


import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.InventarioAgregarRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.InventarioResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Inventario;
import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;
import com.Proyecto.Medic.MedicSalud.Entity.Sede;
import com.Proyecto.Medic.MedicSalud.Repository.InventarioRepository;
import com.Proyecto.Medic.MedicSalud.Repository.MedicamentoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.SedeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final SedeRepository sedeRepository;

    @Transactional
    public InventarioResponseDTO agregarAlInventario(InventarioAgregarRequestDTO req) {

        if (req.getMedicamentoId() == null &&
                (req.getCodigoBarras() == null || req.getCodigoBarras().isBlank())) {
            throw new IllegalArgumentException("Debe enviar medicamentoId o codigoBarras");
        }

        Sede sede = sedeRepository.findById(req.getSedeId())
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));

        Medicamento medicamento;

        if (req.getMedicamentoId() != null) {
            medicamento = medicamentoRepository
                    .findByIdAndEstadoTrue(req.getMedicamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado o inactivo"));
        } else {
            medicamento = medicamentoRepository
                    .findByCodigoBarrasAndEstadoTrue(req.getCodigoBarras())
                    .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado por código de barras o inactivo"));
        }

        Inventario inventario = inventarioRepository
                .findByMedicamento_IdAndSede_Id(medicamento.getId(), sede.getId())
                .orElse(
                        Inventario.builder()
                                .medicamento(medicamento)
                                .sede(sede)
                                .stock(0)
                                .estado(true)
                                .build()
                );

        int nuevoStock = inventario.getStock() + req.getCantidad();
        inventario.setStock(nuevoStock);

        // reglas de estado según stock
        if (nuevoStock <= 0) {
            inventario.setStock(0);
            inventario.setEstado(false);
        } else {
            inventario.setEstado(true);
        }

        Inventario guardado = inventarioRepository.save(inventario);

        return InventarioResponseDTO.fromEntity(guardado);
    }

    @Transactional
    public void actualizarEstadoPorStockCero(Long inventarioId) {
        Inventario inv = inventarioRepository.findById(inventarioId)
                .orElseThrow(() -> new IllegalArgumentException("Inventario no encontrado"));

        if (inv.getStock() <= 0) {
            inv.setStock(0);
            inv.setEstado(false);
            inventarioRepository.save(inv);
        }
    }

    @Transactional
    public void actualizarEstado(Inventario inventario) {
        if (inventario.getStock() <= 0) {
            inventario.setStock(0);
            inventario.setEstado(false);
        } else {
            inventario.setEstado(true);
        }
        inventarioRepository.save(inventario);
    }

    @Transactional
    public List<InventarioResponseDTO> listarInventarioActivo() {
        return inventarioRepository.findByEstadoTrue()
                .stream()
                .map(InventarioResponseDTO::fromEntity)
                .toList();
    }
}
