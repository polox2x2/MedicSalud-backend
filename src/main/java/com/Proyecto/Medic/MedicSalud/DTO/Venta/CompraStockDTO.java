package com.Proyecto.Medic.MedicSalud.DTO.Venta;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompraStockDTO {

    @NotNull
    private Long sedeId;

    @NotNull
    private Long medicamentoId;

    @NotNull
    @Min(1)
    private Integer cantidad;


}
