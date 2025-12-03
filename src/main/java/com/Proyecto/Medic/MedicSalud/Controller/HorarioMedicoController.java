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
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return service.crearHorario(dto, user.getUsername());
    }

    @GetMapping("/mis-horarios")
    public List<HorarioResponseDTO> listarMisHorarios(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return service.listarPorEmail(user.getUsername());
    }

    @GetMapping("/medico")
    public List<HorarioResponseDTO> listarPorEmail(
            @RequestParam(required = false) String email,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        if (email != null && !email.isBlank()) {
            return service.listarPorEmail(email);
        }
        if (user != null) {
            return service.listarPorEmail(user.getUsername());
        }
        throw new IllegalArgumentException("Debe proporcionar un email o estar autenticado");
    }

    @PutMapping("/{id}")
    public HorarioResponseDTO editar(
            @PathVariable Long id,
            @Valid @RequestBody CrearHorarioRequestDTO dto) {
        return service.editarHorario(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarHorario(id);
    }
}
