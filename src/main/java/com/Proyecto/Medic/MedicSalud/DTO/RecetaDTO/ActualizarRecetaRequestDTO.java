package com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActualizarRecetaRequestDTO {

    @NotNull(message = "El id del medicamento es obligatorio")
    private Long medicamentoId;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    private String indicaciones;

}
