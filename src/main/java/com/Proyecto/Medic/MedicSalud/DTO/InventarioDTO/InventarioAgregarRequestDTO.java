package com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioAgregarRequestDTO {

    @NotNull(message = "La sede es obligatoria")
    private Long sedeId;

    // Opción 1: buscar por id del medicamento
    private Long medicamentoId;

    // Opción 2: buscar por código de barras
    private String codigoBarras;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidad;
}
