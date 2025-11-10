package com.Proyecto.Medic.MedicSalud.Controller;


import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoCreateDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoResponseDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoUpdateDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;
import com.Proyecto.Medic.MedicSalud.Service.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    private final MedicamentoService service;

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> crear(@RequestBody MedicamentoCreateDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> actualizar(@PathVariable Long id, @RequestBody MedicamentoUpdateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Long id) {
        service.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }

    // activos con paginación y búsqueda opcional ?q=
    @GetMapping("/activos")
    public ResponseEntity<Page<MedicamentoResponseDTO>> activos(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(required = false) String q) {
        return ResponseEntity.ok(service.listarActivos(page, size, q));
    }

    // por stock en una sede: ?sedeId=1&minStock=1
    @GetMapping("/stock")
    public ResponseEntity<List<MedicamentoResponseDTO>> porStock(@RequestParam Long sedeId, @RequestParam(required = false) Integer minStock) {
        return ResponseEntity.ok(service.listarPorStock(sedeId, minStock));
    }
    @GetMapping("/lista")
    public ResponseEntity<List<?>>todoLosMedicamentos(){
        return ResponseEntity.ok(service.listaGeneral());
    }

    @GetMapping("/lista/activos")
    public ResponseEntity<List<MedicamentoResponseDTO>>todoLosMedicamentosActivos(){
        return ResponseEntity.ok(service.listaDeActivos());
    }


    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
