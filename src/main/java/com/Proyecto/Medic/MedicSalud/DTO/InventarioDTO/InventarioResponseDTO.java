package com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO;


import com.Proyecto.Medic.MedicSalud.Entity.Inventario;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventarioResponseDTO {

    private Long id;
    private Long sedeId;
    private String sedeNombre;

    private Long medicamentoId;
    private String medicamentoNombre;

    private Integer stock;
    private Boolean estado;

    public static InventarioResponseDTO fromEntity(Inventario inv) {
        return InventarioResponseDTO.builder()
                .id(inv.getId())
                .sedeId(inv.getSede().getId())
                .sedeNombre(inv.getSede().getNombreClinica())
                .medicamentoId(inv.getMedicamento().getId())
                .medicamentoNombre(inv.getMedicamento().getNombre())
                .stock(inv.getStock())
                .estado(inv.getEstado())
                .build();
    }

}
