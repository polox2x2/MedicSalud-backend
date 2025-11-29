package com.Proyecto.Medic.MedicSalud.core.Paypal.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrearOrdenResponse {

    private String paypalOrderId;
    private String approveLink;
    private String status;
}
