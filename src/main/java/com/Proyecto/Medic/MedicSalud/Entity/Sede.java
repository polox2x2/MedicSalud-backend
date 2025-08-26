package com.Proyecto.Medic.MedicSalud.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sedes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreClinica;

    private String direccion;

    //vinculacion con otra tabla
    @OneToMany(mappedBy = "sede")
    private List<Medico>medicos;

    @OneToMany (mappedBy = "sede")
    private List<Reserva>reservas;


}
