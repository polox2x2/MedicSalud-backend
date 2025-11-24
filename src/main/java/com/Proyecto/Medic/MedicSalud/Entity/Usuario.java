package com.Proyecto.Medic.MedicSalud.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "usuarios")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //  evita incluir colecciones
@ToString(onlyExplicitlyIncluded = true)
public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "nombre",length = 50, nullable = true )
        private String nombre;
        @Column(name = "apellido",length = 50, nullable = true )
        private String apellido;
        @Column(name = "dni",unique = true,length = 8)
        @NotNull(message = "El dni es obligatorio")
        @Min(value =10000000 ,message = "El DNI debe tene 8 digitos" )
        @Max(value =99999999 ,message = "El DNI debe tener 8 digitos")
        private Integer dni;

        @NotBlank (message = "El email es obligatorio")
        @Email (message = "el formato no coincide")
        private String email;
        @NotBlank(message = "la contraseña es obligatoria")
        private String clave;


        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate fechaNacimiento;

        @Pattern(regexp = "\\d{9}", message = "el telefono debe tener 9 digitos")
        private String telefono;

        private String direccion;

        private LocalDateTime fechaCreacion;

        private String especialidad;

        @Lob
        @Column(name = "foto_perfil", nullable = true)
        private byte[] fotoPerfil;


        private Boolean estado = true;


        //Vinculacion con otra tabla

        //Relación con Roles

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable (name = "Rol_Usuario",
                joinColumns = @JoinColumn(name = "id_usuarios"),
                inverseJoinColumns = @JoinColumn (name = "id_rol")
        )

        @Builder.Default
        @JsonManagedReference  // Evita recursión
        private Set<Rol> roles = new HashSet<>();


}
