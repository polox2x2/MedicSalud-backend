package com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReprogramarReservaDTO {
    private LocalDate fechaCita;
    private LocalTime horaCita;
}
