package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "pacientes")
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreUsuario;

    @OneToOne
    @JoinColumn(name = "usuario_id",insertable = false,unique = true)
    private Usuario usuario;

    private Boolean estado=true;

    @OneToMany(mappedBy = "paciente")
    private List<Historial> historialMedico;

}
