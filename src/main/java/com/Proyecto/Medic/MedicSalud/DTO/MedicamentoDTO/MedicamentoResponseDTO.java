package com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MedicamentoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioVenta;
    private String codigoBarras;
    private Boolean requiereReceta;
    private Boolean estado;
}
