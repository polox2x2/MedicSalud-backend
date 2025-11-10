package com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedicamentoUpdateDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal precioVenta;
    private String codigoBarras;
    private Boolean requiereReceta;
    private Boolean estado;
}
