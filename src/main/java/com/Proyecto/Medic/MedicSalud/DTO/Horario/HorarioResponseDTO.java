package com.Proyecto.Medic.MedicSalud.DTO.Horario;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioResponseDTO {

    private Long id;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private String medicoDni;
}
