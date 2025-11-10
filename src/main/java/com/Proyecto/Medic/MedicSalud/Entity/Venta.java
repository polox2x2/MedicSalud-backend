package com.Proyecto.Medic.MedicSalud.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @ManyToOne(optional = false)         // la venta se hace en una sede
    @JoinColumn(name = "sede_id")
    private Sede sede;

    @ManyToOne(optional = true)          // opcional: asociar al paciente
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleVenta> detalles = new ArrayList<>();

    @Column(nullable = false)
    private Boolean estado = true;

    public void addDetalle(DetalleVenta d) {
        d.setVenta(this);
        this.detalles.add(d);
    }
}
