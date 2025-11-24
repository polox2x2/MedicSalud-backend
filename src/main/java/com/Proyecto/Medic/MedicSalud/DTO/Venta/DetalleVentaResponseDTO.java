package com.Proyecto.Medic.MedicSalud.DTO.Venta;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DetalleVentaResponseDTO {

    private Long medicamentoId;
    private String medicamentoNombre;

    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;


}
