package com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioDTO {
    private Long id;
    private Long sedeId;
    private Long medicamentoId;
    private Integer stock;
    private Boolean estado;
}
