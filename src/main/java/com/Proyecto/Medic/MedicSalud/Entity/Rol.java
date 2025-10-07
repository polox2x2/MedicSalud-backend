package com.Proyecto.Medic.MedicSalud.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;


    //union con otra tabla

    @ManyToMany (mappedBy = "roles")
    @JsonBackReference   // Ignora el "camino de vuelta"
    private Set<Usuario>usuarios = new HashSet<>();


}
