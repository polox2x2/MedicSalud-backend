package com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearHistorialRequestDTO {

    @NotNull
    private Integer pacienteDni;

    @NotNull
    private String diagnostico;

    private String tratamiento;

    private String observaciones;
}
