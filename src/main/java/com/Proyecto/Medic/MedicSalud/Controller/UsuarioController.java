package com.Proyecto.Medic.MedicSalud.Controller;

import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.RegistroUsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioUpDateDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ===== OpenAPI / Swagger =====
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Map;

/*

@RestController
→ Indica que la clase es un controlador REST y devuelve datos en formato JSON.

@RequestMapping("api/usuarios")
→ Define la ruta base para todos los endpoints del controlador.

@GetMapping / @PostMapping / @PutMapping / @DeleteMapping
→ Especifican el tipo de petición HTTP (obtener, crear, actualizar o eliminar).

@PathVariable
→ Permite recibir datos desde la URL (por ejemplo: /usuarios/{id}).

@RequestBody
→ Recibe los datos enviados en el cuerpo del request (en formato JSON).

@ResponseEntity
→ Devuelve la respuesta HTTP con su código de estado (200, 201, 400, etc.).

@RequiredArgsConstructor
→ Crea automáticamente un constructor con los atributos "final"
   (se usa para inyección de dependencias sin @Autowired).

@Tag, @Operation, @ApiResponse, @ApiResponses
→ Son anotaciones de Swagger (OpenAPI) para documentar los endpoints
   en la interfaz Swagger UI.

@Hidden
→ Oculta un endpoint en la documentación Swagger (para uso interno).

===============================================================================
*/
@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // devuelve entidades completas (puede filtrar datos sensibles)
    @Hidden
    @GetMapping("/todos")
    public ResponseEntity<List<Usuario>> listarTodo(){
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @GetMapping
    @Operation(
            summary = "Listar usuarios activos",
            description = "Devuelve la lista de usuarios activos en formato seguro (DTO)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class))))
    })
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarActivos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Devuelve un usuario (DTO) según su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public ResponseEntity<UsuarioDTO> buscar(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Crear usuario",
            description = "Crea un usuario a partir de un DTO. Retorna el usuario creado (DTO)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<UsuarioDTO> crear(
            @RequestBody(description = "Datos del usuario a crear", required = true,
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody UsuarioDTO usuarioDTO) {

        UsuarioDTO creado = usuarioService.crear(usuarioDTO);
        // Devuelve 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/crear-paciente")
    @Operation(
            summary = "Registrar usuario como PACIENTE",
            description = "Crea el usuario y le asigna el rol PACIENTE. Devuelve email, roles y bandera de creación de paciente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<?> crearUsuaroConPaciente(
            @RequestBody(description = "Datos para registro de usuario y paciente", required = true,
                    content = @Content(schema = @Schema(implementation = RegistroUsuarioDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody RegistroUsuarioDTO dto) {


        Usuario usuario = usuarioService.registrarUsuarioComoPaciente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "email", usuario.getEmail(),
                "roles", usuario.getRoles(),
                "pacienteCreado", true
        ));
    }


    @PostMapping("/crear-medico")
    @Operation(
            summary = "Registrar usuario como MÉDICO",
            description = "Crea el usuario y le asigna el rol MÉDICO. Devuelve email, roles y bandera de creación de médico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<?> crearUsuarioConMedico(
            @RequestBody(description = "Datos para registro de usuario y médico", required = true,
                    content = @Content(schema = @Schema(implementation = RegistroUsuarioDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody RegistroUsuarioDTO dto) {

        Usuario usuario = usuarioService.registrarUsuarioComoMedico(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "email", usuario.getEmail(),
                "roles", usuario.getRoles(),
                "medicoCreado", true
        ));
    }




    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza los datos de un usuario (DTO) por ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualizado",
                    content = @Content(schema = @Schema(implementation = UsuarioUpDateDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<UsuarioUpDateDTO> actualizar(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long id,
            @RequestBody(description = "Nuevos datos del usuario", required = true,
                    content = @Content(schema = @Schema(implementation = UsuarioUpDateDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody UsuarioUpDateDTO usuarioDTO) {

        return ResponseEntity.ok(usuarioService.actualizar(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar (borrado lógico) usuario",
            description = "Marca al usuario como inactivo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long id) {
        usuarioService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }


    //Provicional

    @PostMapping("/crear-admin")
    @Operation(
            summary = "Registrar usuario como ADMIN",
            description = "Crea el usuario y le asigna el rol ADMIN. Devuelve email, roles y bandera de creación de admin."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<?> crearUsuaroAdimin(
            @RequestBody(description = "Datos para registro de usuario y admin", required = true,
                    content = @Content(schema = @Schema(implementation = RegistroUsuarioDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody RegistroUsuarioDTO dto) {


        Usuario usuario = usuarioService.registrarUsuarioComoAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "email", usuario.getEmail(),
                "roles", usuario.getRoles(),
                "AdminCreado", true
        ));
    }




}
