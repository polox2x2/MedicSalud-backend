package com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO;


import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.RegistroUsuarioDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para registrar un médico junto con su usuario asociado")
public class RegistroMedicoDTO {

    @Schema(description = "Datos del usuario que se asociará al médico", required = true)
    private RegistroUsuarioDTO usuario;

    @Schema(description = "Estado del médico (activo/inactivo)", example = "true")
    private Boolean estado = true;
}


