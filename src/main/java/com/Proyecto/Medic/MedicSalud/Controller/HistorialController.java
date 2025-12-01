package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.ActualizarHistorialRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.CrearHistorialRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.HistorialDTO.HistorialResponseDTO;
import com.Proyecto.Medic.MedicSalud.Service.HistorialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialService historialService;


    @PostMapping
    public ResponseEntity<HistorialResponseDTO> crear(
            @Valid @RequestBody CrearHistorialRequestDTO dto,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(historialService.crearHistorial(dto, token));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<HistorialResponseDTO>> listarActivos() {
        return ResponseEntity.ok(historialService.listarActivos());
    }

    @GetMapping("/paciente/{dni}")
    public ResponseEntity<List<HistorialResponseDTO>> listarPorPacienteDni(@PathVariable Integer dni) {
        return ResponseEntity.ok(historialService.listarPorPacienteDni(dni));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historialService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialResponseDTO> actualizar(@PathVariable Long id,
                                                           @RequestBody ActualizarHistorialRequestDTO dto) {
        return ResponseEntity.ok(historialService.actualizarHistorial(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Long id) {
        historialService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }


}
