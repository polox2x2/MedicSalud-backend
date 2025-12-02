package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaPacienteDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import com.Proyecto.Medic.MedicSalud.Mappers.ReservaMapper;
import com.Proyecto.Medic.MedicSalud.Service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Validated
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(
            summary = "Crear una reserva",
            description = "Crea una reserva para el PACIENTE logueado evitando solapamientos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reserva creada",
                            content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "409", description = "Médico ocupado en esa fecha/hora")
            }
    )
    @PostMapping("/paciente")
    public ResponseEntity<?> crearReservaPaciente(
            @RequestBody CrearReservaPacienteDTO dto
    ) {
        try {
            ReservaResponseDTO response = reservaService.crearReservaPaciente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/crear")
    public ResponseEntity<ReservaResponseDTO> crear(
            @Valid @RequestBody CrearReservaDTO req
    ) {
        var r = reservaService.crearReserva(req);
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @Operation(
            summary = "Listar mis reservas",
            description = "Devuelve las reservas del paciente autenticado, ordenadas por fecha de cita DESC."
    )
    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaResponseDTO>> listarMisReservas() {
        var list = reservaService.listarReservasPacienteLogueado();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Listar todas las reservas activas",
            description = "Solo para administración."
    )
    @GetMapping("/lista")
    public ResponseEntity<List<ReservaResponseDTO>> listaPorEstadoTrue() {
        return ResponseEntity.ok(reservaService.listarActivos());
    }

    @Operation(
            summary = "Cancelar una reserva",
            description = "Marca la reserva como cancelada (estadoCita=false)."
    )
    @PostMapping("/cancelar/{reservaId}")
    public ResponseEntity<ReservaResponseDTO> cancelar(
            @Parameter(description = "ID de la reserva", required = true)
            @PathVariable Long reservaId
    ) {
        var r = reservaService.cancelar(reservaId);
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
