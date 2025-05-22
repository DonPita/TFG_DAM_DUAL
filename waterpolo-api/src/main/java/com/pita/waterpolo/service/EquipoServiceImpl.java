package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.EquipoRequest;
import com.pita.waterpolo.dto.response.EquipoResponse;
import com.pita.waterpolo.entity.Equipo;
import com.pita.waterpolo.mapper.EquipoMapper;
import com.pita.waterpolo.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final EquipoMapper equipoMapper;

    public EquipoServiceImpl(EquipoRepository equipoRepository, EquipoMapper equipoMapper) {
        this.equipoRepository = equipoRepository;
        this.equipoMapper = equipoMapper;
    }

    @Override
    public List<EquipoResponse> findAll() {
        List<Equipo> equipos = equipoRepository.findAll();

        List<EquipoResponse> equiposDTO = equipos.stream()
                .map(equipo -> equipoMapper.toEquipoResponse(equipo))
                .toList();

        return equiposDTO;
    }

    @Override
    public Optional<EquipoResponse> findById(Integer id) {
        return equipoRepository.findById(id)
                .map(equipo -> equipoMapper.toEquipoResponse(equipo));

    }

    @Override
    public Optional<Equipo> findEntityById(Integer id) {
        return equipoRepository.findById(id);
    }

    @Override
    public EquipoResponse save(EquipoRequest equipoRequest) {
        Equipo equipo = equipoMapper.toEquipo(equipoRequest);

        Equipo savedEquipo = equipoRepository.save(equipo);

        return equipoMapper.toEquipoResponse(savedEquipo);
    }

    @Override
    public EquipoResponse update(EquipoRequest equipoRequest, Optional<Equipo> equipoOptional) {
        Equipo equipo = equipoOptional.get();
        equipo.setNombre(equipoRequest.nombre());
        equipo.setCiudad(equipoRequest.ciudad());
        equipo.setFechaFundacion(equipoRequest.fechaFundacion());

        equipoRepository.save(equipo);

        return equipoMapper.toEquipoResponse(equipo);
    }


    @Override
    public void delete(Integer id) {
        equipoRepository.deleteById(id);
    }
}
