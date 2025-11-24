package com.Proyecto.Medic.MedicSalud.DTO.Venta;


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
    private String sedeNombre;

    private Long pacienteId;
    private String pacienteNombre;
    private Integer pacienteDni;

    private BigDecimal total;

    private List<DetalleVentaResponseDTO> items;
}
