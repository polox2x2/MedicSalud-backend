package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Medicos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombreUsuario;
    @Column(nullable = false, length = 100)
    private String especialidad;
    @Pattern(regexp = "\\d{9}", message = "el telefono debe tener 9 digitos")
    private String telefono;

    private Boolean estado = true;

    //apartado para juntar las tablas

    @OneToOne
    @JoinColumn(name = "usuario_id",nullable = false,unique = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_sede")
    private Sede sede;
}
