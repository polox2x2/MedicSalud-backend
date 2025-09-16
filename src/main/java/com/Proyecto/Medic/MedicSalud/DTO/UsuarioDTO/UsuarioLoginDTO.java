package com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioLoginDTO {

    private String email;
    private String clave;
}
