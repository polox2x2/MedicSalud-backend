package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.Service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;


    @GetMapping("/todos")
    public ResponseEntity<List<?>> listaMedicos(){
        return ResponseEntity.ok(medicoService.listaCompleta());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<?>>listaMedicosActivos(){
        return ResponseEntity.ok(medicoService.listaActivos());
    }

}
