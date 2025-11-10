package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(
        name = "inventarios",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_inventario_medicamento_sede",
                columnNames = {"medicamento_id", "sede_id"}
        )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // FK a Medicamento
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @Min(0)
    @Column(nullable = false)
    private Integer stock = 0;

    @Column(nullable = false)
    private Boolean estado = true;
}
