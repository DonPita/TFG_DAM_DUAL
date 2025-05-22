package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.EquipoLigaRequest;
import com.pita.waterpolo.dto.response.EquipoLigaResponse;
import com.pita.waterpolo.entity.Equipo;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Liga;
import com.pita.waterpolo.mapper.EquipoLigaMapper;
import com.pita.waterpolo.repository.EquipoLigaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoLigaServiceImpl implements EquipoLigaService {

    private final EquipoLigaRepository equipoLigaRepository;
    private final EquipoLigaMapper equipoLigaMapper;

    public EquipoLigaServiceImpl(EquipoLigaRepository equipoLigaRepository, EquipoLigaMapper equipoLigaMapper) {
        this.equipoLigaRepository = equipoLigaRepository;
        this.equipoLigaMapper = equipoLigaMapper;
    }

    @Override
    public List<EquipoLigaResponse> findAll() {
        List<EquipoLiga> equipos = equipoLigaRepository.findAll();

        return equipos.stream()
                .map(equipoLiga -> equipoLigaMapper.toEquipoLigaResponse(equipoLiga))
                .toList();
    }

    @Override
    public Optional<EquipoLigaResponse> findById(Integer id) {
        return equipoLigaRepository.findById(id)
                .map(equipoLiga -> equipoLigaMapper.toEquipoLigaResponse(equipoLiga));
    }

    @Override
    public Optional<EquipoLiga> findEntityById(Integer id) {
        return equipoLigaRepository.findById(id);
    }

    @Override
    public List<EquipoLigaResponse> findByEquipo(Integer idEquipo) {
        List<EquipoLiga> equipos = equipoLigaRepository.findByEquipoId(idEquipo);

        List<EquipoLigaResponse> equiposDTO = equipos.stream()
                .map(equipoLiga -> equipoLigaMapper.toEquipoLigaResponse(equipoLiga)).toList();

        return equiposDTO;
    }

    @Override
    public List<EquipoLigaResponse> findByLiga(Integer idLiga) {
        List<EquipoLiga> equipos = equipoLigaRepository.findByLigaId(idLiga);

        List<EquipoLigaResponse> equiposDTO = equipos.stream()
                .map(equipoLiga -> equipoLigaMapper.toEquipoLigaResponse(equipoLiga)).toList();

        return equiposDTO;
    }

    @Override
    public EquipoLigaResponse save(EquipoLigaRequest equipoLigaRequest, Equipo equipo, Liga liga) {
        EquipoLiga equipoLiga = new EquipoLiga();
        equipoLiga.setEquipo(equipo);
        equipoLiga.setLiga(liga);
        equipoLiga.setNombre(equipoLigaRequest.nombre());
        equipoLiga.setActivo(equipoLigaRequest.activo() != null ? equipoLigaRequest.activo() : true);

        EquipoLiga savedEquipoLiga = equipoLigaRepository.save(equipoLiga);

        return equipoLigaMapper.toEquipoLigaResponse(savedEquipoLiga);
    }

    @Override
    public EquipoLigaResponse update(EquipoLigaRequest equipoLigaRequest, Equipo equipo, Liga liga, Optional<EquipoLiga> equipoLigaOptional) {
        EquipoLiga equipoLiga = equipoLigaOptional.get();
        equipoLiga.setNombre(equipoLigaRequest.nombre());
        equipoLiga.setEquipo(equipo);
        equipoLiga.setLiga(liga);
        equipoLiga.setActivo(equipoLigaRequest.activo());

        EquipoLiga savedEquipoLiga = equipoLigaRepository.save(equipoLiga);

        return equipoLigaMapper.toEquipoLigaResponse(savedEquipoLiga);
    }

    @Override
    public void delete(Integer id) {
        equipoLigaRepository.deleteById(id);
    }
}
