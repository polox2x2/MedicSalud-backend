package com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private Long sedeId;
    private Long pacienteId;
    private BigDecimal total;
    private List<DetalleVentaDTO> detalles;
}
