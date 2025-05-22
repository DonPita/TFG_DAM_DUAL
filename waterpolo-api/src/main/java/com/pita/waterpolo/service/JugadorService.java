package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.JugadorRequest;
import com.pita.waterpolo.dto.response.JugadorResponse;
import com.pita.waterpolo.entity.Jugador;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface JugadorService {

    List<JugadorResponse> findAll();

    Optional<JugadorResponse> findById(Integer id);
    Optional<Jugador> findEntityById(Integer id);

    JugadorResponse save(JugadorRequest jugadorRequest);

    JugadorResponse update(JugadorRequest jugadorRequest, Optional<Jugador> jugadorOptional);

    void delete(Integer id);
}
