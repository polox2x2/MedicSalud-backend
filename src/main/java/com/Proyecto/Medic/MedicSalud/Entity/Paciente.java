package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "pacientes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreUsuario;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @NotNull(message = "El dni es obligatorio")
    @Min(value =10000000 ,message = "El DNI debe tene 8 digitos" )
    @Max(value =99999999 ,message = "El DNI debe tener 8 digitos")
    private Integer dni;

    private Boolean estado=true;

    @Lob
    @Column(name = "foto_perfil", nullable = true)
    private byte[] fotoPerfil;

    @OneToMany(mappedBy = "paciente")
    private List<Historial> historialMedico;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receta> recetas;
}
