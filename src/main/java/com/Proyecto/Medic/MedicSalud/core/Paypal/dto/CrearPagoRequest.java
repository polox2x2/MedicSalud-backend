package com.Proyecto.Medic.MedicSalud.core.Paypal.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CrearPagoRequest {

    private BigDecimal monto;
    private String moneda;

    private Long referenciaId;
    private String referenciaTipo;

}
