package com.Proyecto.Medic.MedicSalud.DTO.Venta;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CrearVentaDTO {

    @NotNull
    private Long sedeId;

    // opcional: venta asociada a un paciente
    private Integer pacienteDni;

    @NotEmpty
    private List<VentaItemDTO> items;

}
