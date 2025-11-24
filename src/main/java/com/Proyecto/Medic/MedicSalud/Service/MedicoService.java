package com.Proyecto.Medic.MedicSalud.Service;

import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.BloqueHorarioDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.DisponibilidadMedicoDTO;
import com.Proyecto.Medic.MedicSalud.DTO.MedicoDTO.MedicoRequestDTO;
import com.Proyecto.Medic.MedicSalud.Entity.HorarioMedico;
import com.Proyecto.Medic.MedicSalud.Entity.Medico;
import com.Proyecto.Medic.MedicSalud.Entity.Reserva;
import com.Proyecto.Medic.MedicSalud.Mappers.MedicoMapper;
import com.Proyecto.Medic.MedicSalud.Repository.MedicoRepository;
import com.Proyecto.Medic.MedicSalud.Repository.RecetaRepository;
import com.Proyecto.Medic.MedicSalud.Repository.ReservaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final RecetaRepository recetaRepo;
    private final ReservaRepository reservaRepository;

    public List<Medico> listaCompleta() {
        return medicoRepository.findAll();
    }

    public List<MedicoRequestDTO> listaActivos() {
        return medicoRepository.findByEstadoTrue()
                .stream()
                .map(MedicoMapper::listaMedicoDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DisponibilidadMedicoDTO obtenerDisponibilidad(Long medicoId, LocalDate fecha) {

        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        DayOfWeek dia = fecha.getDayOfWeek();

        HorarioMedico horarioDia = medico.getHorarios()
                .stream()
                .filter(h -> h.getDia() == dia)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El médico no atiende ese día"));


        List<Reserva> reservas = reservaRepository
                .findByMedico_IdAndFechaCitaOrderByHoraCitaAsc(medicoId, fecha);

        List<LocalTime> horasOcupadas = reservas.stream()
                .filter(Reserva::getEstadoCita)
                .map(Reserva::getHoraCita)
                .toList();

        List<BloqueHorarioDTO> bloques = new ArrayList<>();

        LocalTime hora = horarioDia.getHoraInicio();
        while (!hora.isAfter(horarioDia.getHoraFin())) {
            boolean libre = !horasOcupadas.contains(hora);
            bloques.add(new BloqueHorarioDTO(hora.toString(), libre));
            hora = hora.plusMinutes(30); // intervalo de 30 min
        }

        return DisponibilidadMedicoDTO.builder()
                .medicoId(medico.getId())
                .nombreMedico(medico.getUsuario().getNombre())
                .especialidad(medico.getEspecialidad())
                .nombreSede(medico.getSede() != null ? medico.getSede().getNombreClinica() : null)
                .fecha(fecha)
                .horarios(bloques)
                .build();
    }
}
