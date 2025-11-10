package com.Proyecto.Medic.MedicSalud.Controller;


import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.VentaRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.VentaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crear(@RequestBody VentaRequestDTO request) {
        return ResponseEntity.ok(ventaService.crearVenta(request));
    }

    // Manejo simple de errores comunes
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
