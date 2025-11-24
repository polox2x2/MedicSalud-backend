package com.Proyecto.Medic.MedicSalud.Controller;


import com.Proyecto.Medic.MedicSalud.DTO.Venta.CompraStockDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Venta.CrearVentaDTO;
import com.Proyecto.Medic.MedicSalud.DTO.Venta.VentaResponseDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Venta;
import com.Proyecto.Medic.MedicSalud.Service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Validated
public class VentaController {

    private final VentaService ventaService;


    @PostMapping
    public ResponseEntity<VentaResponseDTO> crearVenta(@RequestBody CrearVentaDTO dto) {
        return ResponseEntity.ok(ventaService.crearVenta(dto));
    }



}
