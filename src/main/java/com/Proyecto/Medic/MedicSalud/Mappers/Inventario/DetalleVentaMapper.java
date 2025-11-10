package com.Proyecto.Medic.MedicSalud.Mappers.Inventario;

import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.DetalleVentaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.VentaItemDTO;
import com.Proyecto.Medic.MedicSalud.Entity.DetalleVenta;
import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;
import com.Proyecto.Medic.MedicSalud.Entity.Venta;

import java.math.BigDecimal;
import java.util.List;

public class DetalleVentaMapper {

    private DetalleVentaMapper() {}

    public static DetalleVentaDTO toDTO(DetalleVenta e) {
        if (e == null) return null;
        return DetalleVentaDTO.builder()
                .medicamentoId(e.getMedicamento().getId())
                .cantidad(e.getCantidad())
                .precioUnitario(e.getPrecioUnitario())
                .subtotal(e.getSubtotal())
                .build();
    }


    public static DetalleVenta toEntity(DetalleVentaDTO dto, Venta venta, Medicamento med) {
        if (dto == null || venta == null || med == null) return null;
        return DetalleVenta.builder()
                .venta(venta)
                .medicamento(med)
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario() != null ? dto.getPrecioUnitario() : med.getPrecioVenta())
                .subtotal(dto.getSubtotal() != null
                        ? dto.getSubtotal()
                        : med.getPrecioVenta().multiply(java.math.BigDecimal.valueOf(dto.getCantidad())))
                .build();
    }

    public static DetalleVenta fromItem(VentaItemDTO item, Venta venta, Medicamento med) {
        BigDecimal precioUnit = med.getPrecioVenta();
        BigDecimal subtotal = precioUnit.multiply(BigDecimal.valueOf(item.getCantidad()));

        return DetalleVenta.builder()
                .venta(venta)
                .medicamento(med)
                .cantidad(item.getCantidad())
                .precioUnitario(precioUnit)
                .subtotal(subtotal)
                .build();
    }

    public static List<DetalleVentaDTO> toDTOList(List<DetalleVenta> list) {
        return list == null ? List.of() : list.stream().map(DetalleVentaMapper::toDTO).toList();
    }
}
