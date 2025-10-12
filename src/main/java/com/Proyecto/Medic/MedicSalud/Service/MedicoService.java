package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.MedicoRequestDTO;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Mappers.MedicoMapper;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicoService {


    private  final MedicoRepository medicoRepository;


    public List<Medico>listaCompleta(){
        return medicoRepository.findAll();
    }
    public List<MedicoRequestDTO> listaActivos(){
        return medicoRepository.findByEstadoTrue()
                .stream()
                .map(MedicoMapper::listaMedicoDTO)
                .collect(Collectors.toList());
    }






}
