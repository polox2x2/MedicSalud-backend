package com.Proyecto.Medic.MedicSalud.Mappers.Inventario;

import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.InventarioDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Inventario;

public class InventarioMapper {

    public static InventarioDTO toDTO(Inventario e) {
        if (e == null) return null;
        return InventarioDTO.builder()
                .id(e.getId())
                .sedeId(e.getSede().getId())
                .medicamentoId(e.getMedicamento().getId())
                .stock(e.getStock())
                .estado(e.getEstado())
                .build();
    }
}
