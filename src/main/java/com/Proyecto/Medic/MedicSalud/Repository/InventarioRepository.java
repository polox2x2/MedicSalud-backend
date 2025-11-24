package com.Proyecto.Medic.MedicSalud.Repository;

import com.Proyecto.Medic.MedicSalud.Entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findBySedeIdAndMedicamentoId(Long sedeId, Long medicamentoId);

    Optional<Inventario> findByMedicamento_IdAndSede_IdAndEstadoTrue(Long medicamentoId, Long sedeId);

    Optional<Inventario> findByMedicamento_IdAndSede_Id(Long medicamentoId, Long sedeId);
    List<Inventario> findByEstadoTrue();





}
