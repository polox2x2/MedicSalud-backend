package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaPacienteDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReprogramarReservaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.EstadoCita;
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

    @Operation(summary = "Crear una reserva", description = "Crea una reserva para el PACIENTE logueado evitando solapamientos", responses = {
            @ApiResponse(responseCode = "200", description = "Reserva creada", content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Médico ocupado en esa fecha/hora")
    })
    @PostMapping("/paciente")
    public ResponseEntity<?> crearReservaPaciente(
            @RequestBody CrearReservaPacienteDTO dto) {
        try {
            ReservaResponseDTO response = reservaService.crearReservaPaciente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<ReservaResponseDTO> crear(
            @Valid @RequestBody CrearReservaDTO req) {
        var r = reservaService.crearReserva(req);
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @Operation(summary = "Listar mis reservas", description = "Devuelve las reservas del paciente autenticado, ordenadas por fecha de cita DESC.")
    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaResponseDTO>> listarMisReservas() {
        var list = reservaService.listarReservasPacienteLogueado();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Listar reservas del médico", description = "Devuelve las reservas del médico autenticado.")
    @GetMapping("/lista")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasMedico() {
        var list = reservaService.listarReservasMedicoLogueado();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Listar reservas por estado", description = "Devuelve las reservas filtradas por estado (PENDIENTE, CONFIRMADA, REPROGRAMADA, RECHAZADA).")
    @GetMapping("/lista/{estado}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorEstado(
            @PathVariable EstadoCita estado) {
        return ResponseEntity.ok(reservaService.listarPorEstado(estado));
    }

    @Operation(summary = "Listar reservas activas", description = "Devuelve todas las reservas con estado = true.")
    @GetMapping("/activas")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasActivas() {
        return ResponseEntity.ok(reservaService.listarReservasActivas());
    }

    @Operation(summary = "Confirmar reserva")
    @PutMapping("/confirmar/{id}")
    public ResponseEntity<ReservaResponseDTO> confirmar(@PathVariable Long id) {
        var r = reservaService.confirmar(id);
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @Operation(summary = "Rechazar reserva")
    @PutMapping("/rechazar/{id}")
    public ResponseEntity<ReservaResponseDTO> rechazar(@PathVariable Long id) {
        var r = reservaService.rechazar(id);
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @Operation(summary = "Poner reserva en pendiente")
    @PutMapping("/pendiente/{id}")
    public ResponseEntity<ReservaResponseDTO> pendiente(@PathVariable Long id) {
        var r = reservaService.pendiente(id);
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @Operation(summary = "Reprogramar reserva")
    @PutMapping("/reprogramar/{id}")
    public ResponseEntity<ReservaResponseDTO> reprogramar(
            @PathVariable Long id,
            @RequestBody ReprogramarReservaDTO dto) {
        var r = reservaService.reprogramar(id, dto.getFechaCita(), dto.getHoraCita());
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @Operation(summary = "Cancelar una reserva", description = "Marca la reserva como RECHAZADA.")
    @PostMapping("/cancelar/{reservaId}")
    public ResponseEntity<ReservaResponseDTO> cancelar(
            @Parameter(description = "ID de la reserva", required = true) @PathVariable Long reservaId) {
        var r = reservaService.cancelar(reservaId);
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
