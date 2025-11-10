package com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    private Long id;
    private LocalDateTime fecha;
    private Long sedeId;
    private BigDecimal total;
    private List<DetalleVentaDTO> detalles;
}
