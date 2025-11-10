package com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicamentoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioVenta;
    private String codigoBarras;
    private Boolean requiereReceta;
    private Boolean estado;
}
