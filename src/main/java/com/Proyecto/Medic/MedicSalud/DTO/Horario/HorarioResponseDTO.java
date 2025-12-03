package com.Proyecto.Medic.MedicSalud.DTO.Horario;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioResponseDTO {

    private Long id;
    private java.time.LocalDate fecha;
    private String horaInicio;
    private String horaFin;
    private String medicoDni;
    private String estado;
}
