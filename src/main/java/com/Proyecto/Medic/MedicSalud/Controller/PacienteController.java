package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteDTO;
import com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacienteResponseDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioRequestDTO;
import com.Proyecto.Medic.MedicSalud.Service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping("crear")
    public ResponseEntity<?> crearPaciente(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try {
            PacienteDTO pacienteDTO = pacienteService.crearPacienteDesdeUsuarioDTO(usuarioRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/obtener")
    public ResponseEntity<?> obtenerPaciente(@RequestBody UsuarioRequestDTO dto) {
        return pacienteService.obtenerPacientePorUsuarioDTO(dto)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Paciente no encontrado " + dto.getDni()));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pacienteService.listaCompleta());
    }

    @GetMapping("/lista")
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obtenerPaciente(@PathVariable Long id) {
        PacienteDTO dto = pacienteService.buscarPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> crearPaciente(@RequestBody PacienteDTO dto) {
        return ResponseEntity.ok(pacienteService.guardar(dto));
    }

    @DeleteMapping("/eliminar/{dni}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer dni) {
        pacienteService.eliminarLogicoPorDni(dni);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/actualizar")
    public ResponseEntity<PacienteDTO> actualizarPaciente(
            @RequestBody com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioUpDateDTO dto) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication()
                .getName();
        return ResponseEntity.ok(pacienteService.actualizarPacientePorEmail(email, dto));
    }

    @GetMapping("/me")
    public ResponseEntity<com.Proyecto.Medic.MedicSalud.DTO.PacienteDTO.PacientePerfilDTO> obtenerMiPerfil() {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication()
                .getName();
        return ResponseEntity.ok(pacienteService.obtenerMiPerfil(email));
    }

}
