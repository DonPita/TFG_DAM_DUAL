package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.JugadorRequest;
import com.pita.waterpolo.dto.response.JugadorResponse;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.mapper.JugadorMapper;
import com.pita.waterpolo.repository.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;
    private final JugadorMapper jugadorMapper;

    public JugadorServiceImpl(JugadorRepository jugadorRepository, JugadorMapper jugadorMapper) {
        this.jugadorRepository = jugadorRepository;
        this.jugadorMapper = jugadorMapper;
    }

    @Override
    public List<JugadorResponse> findAll() {
        List<Jugador> jugadores = jugadorRepository.findAll();

        return jugadores.stream()
                .map(jugador -> jugadorMapper.toResponse(jugador)).toList();
    }

    @Override
    public Optional<JugadorResponse> findById(Integer id) {
        return jugadorRepository.findById(id)
                .map(jugador -> jugadorMapper.toResponse(jugador));
    }

    @Override
    public Optional<Jugador> findEntityById(Integer id) {
        return jugadorRepository.findById(id);
    }

    @Override
    public JugadorResponse save(JugadorRequest jugadorRequest) {
        Jugador jugador = jugadorMapper.toEntity(jugadorRequest);

        Jugador jugadorGuardado = jugadorRepository.save(jugador);

        return jugadorMapper.toResponse(jugadorGuardado);
    }

    @Override
    public JugadorResponse update(JugadorRequest jugadorRequest, Optional<Jugador> jugadorOptional) {
        Jugador jugador = jugadorOptional.get();
        jugador.setNombre(jugadorRequest.nombre());
        jugador.setApellidos(jugadorRequest.apellidos());
        jugador.setFechaNacimiento(jugadorRequest.fechaNacimiento());

        Jugador jugadorGuardado = jugadorRepository.save(jugador);

        return jugadorMapper.toResponse(jugadorGuardado);
    }

    @Override
    public void delete(Integer id) {
        jugadorRepository.deleteById(id);
    }
}
