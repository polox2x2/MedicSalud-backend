package com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO;


import lombok.Data;

@Data
public class ActualizarHistorialRequestDTO {

    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private Boolean estado;
}
