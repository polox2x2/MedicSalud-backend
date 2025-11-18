package com.Proyecto.Medic.MedicSalud.Controller;


import com.Proyecto.Medic.MedicSalud.DTO.Horario.CrearHorarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Horario.HorarioResponseDTO;
import com.Proyecto.Medic.MedicSalud.Service.HorarioMedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
@CrossOrigin("*")
public class HorarioMedicoController {

    private final HorarioMedicoService service;

    @PostMapping
    public HorarioResponseDTO crear(
            @Valid @RequestBody CrearHorarioRequestDTO dto,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user
    ) {
        return service.crearHorario(dto, user.getUsername());
    }


    @GetMapping("/mis-horarios")
    public List<HorarioResponseDTO> listarMisHorarios(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user
    ) {
        return service.listarPorEmail(user.getUsername());
    }

    @GetMapping("/medico")
    public List<HorarioResponseDTO> listarPorEmail(@RequestParam String email) {
        return service.listarPorEmail(email);
    }
}
