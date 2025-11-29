package com.Proyecto.Medic.MedicSalud.core.Paypal.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paypal {

    private String paypalOrderId;

    private BigDecimal monto;

    private String moneda;

    private String estado;

    private Long referenciaId;
    private String referenciaTipo;

    private LocalDateTime fechaCreacion;

}
