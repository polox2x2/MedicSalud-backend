package com.Proyecto.Medic.MedicSalud.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true)
    private String nombre;


    //union con otra tabla

    @ManyToMany (mappedBy = "roles")
    @JsonBackReference
    @ToString.Exclude
    private Set<Usuario>usuarios = new HashSet<>();


}
