package com.Proyecto.Medic.MedicSalud.Service;


import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoCreateDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoResponseDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicamentoDTO.MedicamentoUpdateDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medicamento;
import com.Proyecto.Medic.MedicSalud.Mappers.MedicamentoMapper;
import com.Proyecto.Medic.MedicSalud.Repository.MedicamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepo;

    @Transactional
    public MedicamentoResponseDTO crear(MedicamentoCreateDTO dto) {
        if (dto.getCodigoBarras() != null && medicamentoRepo.existsByCodigoBarras(dto.getCodigoBarras())) {
            throw new IllegalArgumentException("Código de barras ya registrado");
        }
        Medicamento e = MedicamentoMapper.toEntity(dto);
        return MedicamentoMapper.toResponse(medicamentoRepo.save(e));
    }

    @Transactional
    public MedicamentoResponseDTO actualizar(Long id, MedicamentoUpdateDTO dto) {
        Medicamento e = medicamentoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado"));
        if (dto.getCodigoBarras() != null && !dto.getCodigoBarras().equals(e.getCodigoBarras())
                && medicamentoRepo.existsByCodigoBarras(dto.getCodigoBarras())) {
            throw new IllegalArgumentException("Código de barras ya registrado");
        }
        MedicamentoMapper.applyUpdate(dto, e);
        return MedicamentoMapper.toResponse(medicamentoRepo.save(e));
    }

    @Transactional
    public void eliminarLogico(Long id) {
        Medicamento e = medicamentoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado"));
        e.setEstado(false);
        medicamentoRepo.save(e);
    }

    public Page<MedicamentoResponseDTO> listarActivos(int page, int size, String q) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Medicamento> result = (q == null || q.isBlank())
                ? medicamentoRepo.findByEstadoTrue(pageable)
                : medicamentoRepo.findByEstadoTrueAndNombreContainingIgnoreCase(q, pageable);
        return result.map(MedicamentoMapper::toResponse);
    }

    public List<MedicamentoResponseDTO> listarPorStock(Long sedeId, Integer minStock) {
        int min = (minStock == null ? 1 : Math.max(minStock, 0));
        return medicamentoRepo.findActivosConStockPorSede(sedeId, min)
                .stream()
                .map(MedicamentoMapper::toResponse)
                .toList();
    }
    public List<Medicamento>listaGeneral(){
        return medicamentoRepo.findAll();
    }
    public List<MedicamentoResponseDTO>listaDeActivos( ){
        return medicamentoRepo.findByEstadoTrue().stream()
                .map(MedicamentoMapper::toResponse)
                .collect(Collectors.toList());
    }

}
