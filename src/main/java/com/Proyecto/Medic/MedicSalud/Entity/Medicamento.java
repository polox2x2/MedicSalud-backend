package com.Proyecto.Medic.MedicSalud.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "medicamentos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del producto a mostrar en la venta
    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;

    // Opcional, breve
    @Column(length = 255)
    private String descripcion;

    // Precio de venta (sin complicarnos con impuestos por ahora)
    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioVenta;

    // Opcional; si lo usas, que sea único
    @Column(length = 20, unique = true)
    private String codigoBarras;

    // Si requiere receta o no
    @Column(nullable = false)
    private Boolean requiereReceta = false;

    // Activado/desactivado
    @Column(nullable = false)
    private Boolean estado = true;

    // Auditoría mínima
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

}
