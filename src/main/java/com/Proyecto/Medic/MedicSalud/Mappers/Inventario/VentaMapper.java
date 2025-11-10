package com.Proyecto.Medic.MedicSalud.Mappers.Inventario;

import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.DetalleVentaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.VentaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.VentaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.DetalleVenta;
import com.Proyecto.Medic.MedicSalud.Entity.Venta;

import java.util.List;

public class VentaMapper {

    private VentaMapper() {}

    public static VentaResponseDTO toResponse(Venta venta) {
        if (venta == null) return null;

        List<DetalleVentaDTO> detallesDTO = venta.getDetalles()
                .stream()
                .map(d -> DetalleVentaDTO.builder()
                        .medicamentoId(d.getMedicamento().getId())
                        .cantidad(d.getCantidad())
                        .precioUnitario(d.getPrecioUnitario())
                        .subtotal(d.getSubtotal())
                        .build())
                .toList();

        return VentaResponseDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .sedeId(venta.getSede().getId())
                .pacienteId(venta.getPaciente() != null ? venta.getPaciente().getId() : null)
                .total(venta.getTotal())
                .detalles(detallesDTO)
                .build();
    }
}
