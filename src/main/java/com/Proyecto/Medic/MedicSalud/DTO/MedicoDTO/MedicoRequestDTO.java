package com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO;


import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicoRequestDTO {

        private String nombre;
        private String especialidad;
        private Integer dni;
        private String sede;



}
