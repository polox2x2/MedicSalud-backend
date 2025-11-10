package com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedicamentoCreateDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal precioVenta;
    private String codigoBarras;
    private Boolean requiereReceta;
}
