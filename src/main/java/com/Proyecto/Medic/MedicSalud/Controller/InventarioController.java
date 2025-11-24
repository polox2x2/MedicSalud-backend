package com.Proyecto.Medic.MedicSalud.Controller;


import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.InventarioAgregarRequestDTO;
import com.Proyecto.Medic.MedicSalud.DTO.InventarioDTO.InventarioResponseDTO;
import com.Proyecto.Medic.MedicSalud.Service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping("/agregar")
    public ResponseEntity<InventarioResponseDTO> agregar(@Valid @RequestBody InventarioAgregarRequestDTO req) {
        return ResponseEntity.ok(inventarioService.agregarAlInventario(req));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<InventarioResponseDTO>> listarActivos() {
        return ResponseEntity.ok(inventarioService.listarInventarioActivo());
    }



}
