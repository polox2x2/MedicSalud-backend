package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.CrearReservaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.ReservaDTO.ReservaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import com.Proyecto.Medic.MedicSalud.Mappers.ReservaMapper;
import com.Proyecto.Medic.MedicSalud.Service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reservas", description = "Gestión de reservas de citas")
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(
            summary = "Crear una reserva",
            description = "Crea una reserva evitando solapamientos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reserva creada",
                            content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "409", description = "Médico ocupado en esa fecha/hora")
            }
    )
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(
            @Valid @RequestBody // <-- ESTA es la de Spring
            CrearReservaDTO req
    ) {
        Reserva r = reservaService.crearReserva(
                req.getPacienteId(),
                req.getMedicoId(),
                req.getSedeId(),
                req.getFechaCita(),
                req.getHoraCita()
        );
        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }

    @Operation(
            summary = "Listar reservas de un paciente",
            description = "Devuelve las reservas de un paciente ordenadas por fecha de cita descendente."
    )
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorPaciente(
            @Parameter(description = "ID del paciente", required = true)
            @PathVariable Long pacienteId
    ) {
        var list = reservaService.reservasDePaciente(pacienteId)
                .stream()
                .map(ReservaMapper::toResponse )
                .toList();

        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Cancelar una reserva",
            description = "Marca la reserva como cancelada (estadoCita=false)."
    )
    @PostMapping("/{reservaId}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelar(
            @Parameter(description = "ID de la reserva", required = true)
            @PathVariable Long reservaId
    ) {
        var r = reservaService.cancelar(reservaId);

        return ResponseEntity.ok(ReservaMapper.toResponse(r));
    }
}