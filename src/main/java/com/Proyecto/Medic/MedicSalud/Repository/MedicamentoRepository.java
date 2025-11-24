package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    Page<Medicamento> findByEstadoTrue(Pageable pageable);
    Page<Medicamento> findByEstadoTrueAndNombreContainingIgnoreCase(String nombre, Pageable pageable);
    List<Medicamento> findByEstadoTrue();


    boolean existsByCodigoBarras(String codigoBarras);



    Optional<Medicamento> findByIdAndEstadoTrue(Long id);



    Optional<Medicamento> findByCodigoBarrasAndEstadoTrue(String codigoBarras);



    // Medicamentos activos con stock >= minStock en una sede
    @Query("""
           select i.medicamento
             from Inventario i
            where i.sede.id = :sedeId
              and i.stock >= :minStock
              and i.estado = true
              and i.medicamento.estado = true
           """)
    List<Medicamento> findActivosConStockPorSede(@Param("sedeId") Long sedeId,
                                                 @Param("minStock") Integer minStock);

}
