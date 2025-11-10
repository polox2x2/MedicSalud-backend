package com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO;

import lombok.Data;

import java.util.List;

@Data
public class VentaRequestDTO {
    private Long sedeId;
    private Long pacienteId;
    private List<VentaItemDTO> items;
}
