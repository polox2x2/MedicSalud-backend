package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.DisponibilidadMedicoDTO;
import com.Proyecto.Medic.MedicSalud.Service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping("/todos")
    public ResponseEntity<List<?>> listaMedicos() {
        return ResponseEntity.ok(medicoService.listaCompleta());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<?>> listaMedicosActivos() {
        return ResponseEntity.ok(medicoService.listaActivos());
    }

    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<DisponibilidadMedicoDTO> obtenerDisponibilidad(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(
                medicoService.obtenerDisponibilidad(id, fecha));
    }

    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(
            @RequestBody com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.ActualizarMedicoDTO dto) {
        return ResponseEntity.ok(medicoService.actualizarMedicoLogueado(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<?> obtenerMiPerfil() {
        return ResponseEntity.ok(medicoService.obtenerMedicoLogueado());
    }
}
