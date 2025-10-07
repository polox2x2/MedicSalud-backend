package com.Proyecto.Medic.MedicSalud.Service;


import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.RegistroUsuarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.UsuarioDTO.UsuarioDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Paciente;
import com.Proyecto.Medic.MedicSalud.Entity.Rol;
import com.Proyecto.Medic.MedicSalud.Entity.Usuario;
import com.Proyecto.Medic.MedicSalud.Mappers.UsuarioMappers;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.PacienteRepository;
import com.Proyecto.Medic.MedicSalud.Repository.RolRepository;
import com.Proyecto.Medic.MedicSalud.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final MedicoRepository medicoRepository;

    /**
     * Importante :
     *
     * @Transactional -> controlar las transacciones con la base de datos, asegurando que un bloque de operaciones
     * se ejecute como una unidad completa, o que todas se reviertan si algo falla.
     */


    // USUARIO-PACIENTE
    @Transactional
    public Usuario registrarUsuarioComoPaciente(RegistroUsuarioDTO dto) {
        //validacion de correo y dni
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (dto.getDni() != null && usuarioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        //Datos Mapeados
        Usuario usuario = UsuarioMappers.registerUsuarioDTO(dto);

        //Encriptamos la clave
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));

        //Agregamos el rol por defecto
        Rol rolPaciente = rolRepository.findByNombre("PACIENTE")
                .orElseThrow(() -> new RuntimeException("Falta crear el rol PACIENTE en la bd"));
        usuario.getRoles().add(rolPaciente);

        //Guardamos el usuario
        usuario = usuarioRepository.save(usuario);


        //Creamos a obj paciente
        Paciente paciente = new Paciente();

        //les damos los datos de usuario
        paciente.setNombreUsuario(usuario.getNombre() + usuario.getApellido());
        paciente.setUsuario(usuario);

        //guardamos el usuario en paciente
        pacienteRepository.save(paciente);

        return usuario;

    }

    @Transactional
    public Usuario registrarUsuarioComoMedico(RegistroUsuarioDTO dto) {
        // validación de correo y dni
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (dto.getDni() != null && usuarioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        // Datos mapeados
        Usuario usuario = UsuarioMappers.registerUsuarioDTO(dto);


        // Encriptamos la clave
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));


        // Agregamos el rol por defecto MEDICO
        Rol rolMedico = rolRepository.findByNombre("MEDICO")
                .orElseThrow(() -> new RuntimeException("Falta crear el rol MEDICO en la bd"));
        usuario.getRoles().add(rolMedico);


        // Guardamos el usuario
        usuario = usuarioRepository.save(usuario);


        // Creamos obj medico
        Medico medico = new Medico();
        medico.setUsuario(usuario);


        // Guardamos el médico
        medicoRepository.save(medico);

        return usuario;
    }


    //USUARIO

    public List<UsuarioDTO> listarActivos() {
        return usuarioRepository.findByEstadoTrue()
                .stream()
                .map(UsuarioMappers::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioMappers::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioMappers.toEntity(usuarioDTO);
        usuario.setEstado(true); // por defecto activo
        return UsuarioMappers.toDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        return UsuarioMappers.toDTO(usuarioRepository.save(usuario));
    }

    public void eliminarLogico(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(false); // baja lógica
        usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
