package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
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


    //apartado para juntar las tablas

    @OneToOne
    @JoinColumn(name = "dni_usuario",referencedColumnName = "dni")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_sede")
    private Sede sede;
}
