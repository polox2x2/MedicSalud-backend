package com.Proyecto.Medic.MedicSalud.Controller;


import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.RegistroUsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.crear(usuarioDTO));
    }
    @PostMapping("/crear")
    public ResponseEntity<?>crearUsuaroConPaciente (@RequestBody RegistroUsuarioDTO dto){
        Usuario usuario = usuarioService.registrarUsuarioComoPaciente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id",usuario.getId(),
                "email",usuario.getEmail(),
                "roles",usuario.getRoles(),
                "pacienteCreado",true

        ));
    }



    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.actualizar(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }


}
