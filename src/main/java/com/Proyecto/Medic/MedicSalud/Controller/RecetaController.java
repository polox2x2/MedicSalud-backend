package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.ActualizarRecetaRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.CrearRecetaRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.RecetaDTO.RecetaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Service.RecetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaService recetaService;

    @PostMapping
    public ResponseEntity<RecetaResponseDTO> crearReceta(
            @Valid @RequestBody CrearRecetaRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recetaService.crearReceta(dto));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<RecetaResponseDTO>> recetasPorPaciente(
            @PathVariable Long pacienteId) {
        return ResponseEntity.ok(recetaService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<RecetaResponseDTO>> recetasPorMedico(
            @PathVariable Long medicoId) {
        return ResponseEntity.ok(recetaService.listarPorMedico(medicoId));
    }

    @GetMapping("/paciente/dni/{dni}")
    public ResponseEntity<List<RecetaResponseDTO>> recetasPorPacienteDni(
            @PathVariable Integer dni) {
        return ResponseEntity.ok(recetaService.listarPorPacienteDni(dni));
    }

    @GetMapping("/medico/dni/{dni}")
    public ResponseEntity<List<RecetaResponseDTO>> recetasPorMedicoDni(
            @PathVariable Integer dni) {
        return ResponseEntity.ok(recetaService.listarPorMedicoDni(dni));
    }

    @GetMapping("/paciente/me")
    public ResponseEntity<List<RecetaResponseDTO>> recetasPacienteActual() {
        return ResponseEntity.ok(recetaService.listarRecetasPacienteActual());
    }

    @GetMapping("/medico/me")
    public ResponseEntity<List<RecetaResponseDTO>> recetasMedicoActual() {
        return ResponseEntity.ok(recetaService.listarRecetasMedicoActual());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> actualizarReceta(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarRecetaRequestDTO dto) {
        return ResponseEntity.ok(recetaService.actualizarReceta(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(
            @PathVariable Long id) {
        recetaService.eliminarRecetaLogica(id);
        return ResponseEntity.noContent().build();
    }
}
