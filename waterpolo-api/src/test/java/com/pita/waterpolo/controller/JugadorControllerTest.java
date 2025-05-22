package com.pita.waterpolo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pita.waterpolo.dto.request.JugadorRequest;
import com.pita.waterpolo.dto.response.JugadorResponse;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.service.JugadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JugadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JugadorService jugadorService;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    void testGetAll_Authenticated_Empty() throws Exception {
        when(jugadorService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/jugadores"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAll_Authenticated_WithJugadores() throws Exception {
        JugadorResponse jugador = new JugadorResponse(1, "Manuel", "Perez Lopez", LocalDate.of(2000, 1,1));
        when(jugadorService.findAll()).thenReturn(List.of(jugador));

        mockMvc.perform(get("/jugadores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Manuel"))
                .andExpect(jsonPath("$[0].apellidos").value("Perez Lopez"))
                .andExpect(jsonPath("$[0].fechaNacimiento").value("01/01/2000"));
    }

    @Test
    void testGetAll_Unauthenticated() throws Exception {
        mockMvc.perform(get("/jugadores"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void testGetById_Authenticated_Success() throws Exception {
        JugadorResponse jugador = new JugadorResponse(1, "Manuel", "García García", LocalDate.of(2000, 1, 1));
        when(jugadorService.findById(1)).thenReturn(Optional.of(jugador));

        mockMvc.perform(get("/jugadores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Manuel"))
                .andExpect(jsonPath("$.apellidos").value("García García"))
                .andExpect(jsonPath("$.fechaNacimiento").value("01/01/2000"));
    }

    @Test
    @WithMockUser
    void testGetById_Authenticated_NotFound() throws Exception {
        when(jugadorService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/jugadores/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetById_Unauthenticated() throws Exception {
        mockMvc.perform(get("/jugadores/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate_Admin_Success() throws Exception {
        JugadorRequest request = new JugadorRequest("Manuel", "García García", LocalDate.of(2000, 1, 1));
        JugadorResponse response = new JugadorResponse(1, "Manuel", "García García", LocalDate.of(2000, 1, 1));
        when(jugadorService.save(any(JugadorRequest.class))).thenReturn(response);

        mockMvc.perform(post("/jugadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api-v0/jugadores/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Manuel"))
                .andExpect(jsonPath("$.apellidos").value("García García"))
                .andExpect(jsonPath("$.fechaNacimiento").value("01/01/2000"));
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testCreate_Arbitro_Forbidden() throws Exception {
        JugadorRequest request = new JugadorRequest("Manuel", "García García", LocalDate.of(2000, 1, 1));

        mockMvc.perform(post("/jugadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_Admin_Success() throws Exception {
        JugadorRequest request = new JugadorRequest("Ana", "López Pérez", LocalDate.of(1999, 5, 15));
        Jugador jugador = new Jugador(1, "Manuel", "García García", LocalDate.of(2000, 1, 1));
        JugadorResponse response = new JugadorResponse(1, "Ana", "López Pérez", LocalDate.of(1999, 5, 15));
        when(jugadorService.findEntityById(1)).thenReturn(Optional.of(jugador));
        when(jugadorService.update(any(JugadorRequest.class), any(Optional.class))).thenReturn(response);

        mockMvc.perform(put("/jugadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Ana"))
                .andExpect(jsonPath("$.apellidos").value("López Pérez"))
                .andExpect(jsonPath("$.fechaNacimiento").value("15/05/1999"));
    }

    @Test
    @WithMockUser(roles = "USUARIO")
    void testUpdate_Usuario_Forbidden() throws Exception {
        JugadorRequest request = new JugadorRequest("Ana", "López Pérez", LocalDate.of(1999, 5, 15));

        mockMvc.perform(put("/jugadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_NotFound() throws Exception {
        JugadorRequest request = new JugadorRequest("Ana", "López Pérez", LocalDate.of(1999, 5, 15));
        when(jugadorService.findEntityById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/jugadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_Admin_Success() throws Exception {
        Jugador jugador = new Jugador(1, "Manuel", "García García", LocalDate.of(2000, 1, 1));
        when(jugadorService.findEntityById(1)).thenReturn(Optional.of(jugador));
        doNothing().when(jugadorService).delete(1);

        mockMvc.perform(delete("/jugadores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testDelete_Arbitro_Forbidden() throws Exception {
        mockMvc.perform(delete("/jugadores/1"))
                .andExpect(status().isForbidden());
    }
}
