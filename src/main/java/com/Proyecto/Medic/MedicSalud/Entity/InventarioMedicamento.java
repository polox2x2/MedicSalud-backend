package com.Proyecto.Medic.MedicSalud.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventario_medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioMedicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el medicamento
    @ManyToOne
    @JoinColumn(name = "id_medicamento", nullable = false)
    private Medicamento medicamento;

    // Relación con la sede
    @ManyToOne
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    // Stock actual
    @Column(nullable = false)
    private Integer stock;

    // Activo / Inactivo
    @Column(nullable = false)
    private Boolean estado = true;
}
