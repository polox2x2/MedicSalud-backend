package com.Proyecto.Medic.MedicSalud.Mappers;


import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoCreateDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoResponseDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoUpdateDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;

public class MedicamentoMapper {

    private MedicamentoMapper() {}

    public static Medicamento toEntity(MedicamentoCreateDTO dto) {
        if (dto == null) return null;
        return Medicamento.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precioVenta(dto.getPrecioVenta())
                .codigoBarras(dto.getCodigoBarras())
                .requiereReceta(dto.getRequiereReceta() != null ? dto.getRequiereReceta() : false)
                .estado(true)
                .build();
    }

    public static void applyUpdate(MedicamentoUpdateDTO dto, Medicamento e) {
        if (dto == null || e == null) return;
        if (dto.getNombre() != null) e.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) e.setDescripcion(dto.getDescripcion());
        if (dto.getPrecioVenta() != null) e.setPrecioVenta(dto.getPrecioVenta());
        if (dto.getCodigoBarras() != null) e.setCodigoBarras(dto.getCodigoBarras());
        if (dto.getRequiereReceta() != null) e.setRequiereReceta(dto.getRequiereReceta());
        if (dto.getEstado() != null) e.setEstado(dto.getEstado());
    }

    public static MedicamentoResponseDTO toResponse(Medicamento e) {
        if (e == null) return null;
        return MedicamentoResponseDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .descripcion(e.getDescripcion())
                .precioVenta(e.getPrecioVenta())
                .codigoBarras(e.getCodigoBarras())
                .requiereReceta(e.getRequiereReceta())
                .estado(e.getEstado())
                .build();
    }
}
