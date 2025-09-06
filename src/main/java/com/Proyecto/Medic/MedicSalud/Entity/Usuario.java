package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nombre",length = 50, nullable = true )
        private String nombre;

        @Column(name = "apellido",length = 50, nullable = true )
        private String apellido;

        @Column(name = "dni", unique = true, nullable = false)
        @NotNull(message = "El DNI es obligatorio")
        @Min(value = 10000000, message = "El DNI debe tener 8 dígitos")
        @Max(value = 99999999, message = "El DNI debe tener 8 dígitos")
        private Integer dni;

        @NotNull
        @NotBlank (message = "El email es obligatorio")
        @Email (message = "el formato no coincide")
        private String email;

        @Size(max =16 , min = 8 , message = "La contraseña debe tener por lo menos 8 caracteres y un maximo de 16")
        @NotBlank(message = "la contraseña es obligatoria")
        private String clave;

        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        private LocalDate fechaNacimiento;

        @Pattern(regexp = "\\d{9}", message = "el telefono debe tener 9 digitos")
        private String telefono;

        private String direccion;

        private LocalDateTime fechaCreacion;

        @Column(nullable = false)
        private boolean estado = true;


        //Vinculacion con otra tabla

        //Relación con Roles

        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(
                name = "Rol_Usuario",
                joinColumns = @JoinColumn(name = "id_usuarios"),
                inverseJoinColumns = @JoinColumn(name = "id_rol")
        )
        private Set<Rol> roles = new HashSet<>();


}
