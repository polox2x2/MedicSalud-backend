package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Medicos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La especialidad es obligatoria")
    @Column(nullable = false, length = 100)
    private String especialidad;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado = true;

    @NotNull(message = "El DNI es obligatorio")
    @Min(value = 10000000, message = "El DNI debe tener 8 dígitos")
    @Max(value = 99999999, message = "El DNI debe tener 8 dígitos")
    private Integer dni;

    @Lob
    @Column(name = "foto_perfil")
    private byte[] fotoPerfil;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @NotNull(message = "El usuario asociado es obligatorio")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_sede", nullable = false)
    @NotNull(message = "La sede es obligatoria")
    private Sede sede;

    @OneToMany(mappedBy = "medico")
    private List<Receta> recetasEmitidas;


    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioMedico> horarios;


    @OneToMany(mappedBy = "medico")
    private List<Reserva> reservas;
}
