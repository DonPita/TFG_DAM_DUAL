package com.pita.waterpolo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pita.waterpolo.dto.request.TemporadaRequest;
import com.pita.waterpolo.dto.response.TemporadaResponse;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.service.TemporadaService;
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
public class TemporadaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TemporadaService temporadaService;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    void testGetAll_Authenticated_Empty() throws Exception {
        when(temporadaService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/temporadas"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAll_Authenticated_WithTemporadas() throws Exception {
        TemporadaResponse temporada = new TemporadaResponse(1, "2025-2026", LocalDate.of(2025, 9, 1), LocalDate.of(2026, 8, 31));
        when(temporadaService.findAll()).thenReturn(List.of(temporada));

        mockMvc.perform(get("/temporadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("2025-2026"))
                .andExpect(jsonPath("$[0].fechaInicio").value("01/09/2025"))
                .andExpect(jsonPath("$[0].fechaFin").value("31/08/2026"));
    }

    @Test
    void testGetAll_Unauthenticated() throws Exception {
        mockMvc.perform(get("/temporadas"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void testGetById_Authenticated_Success() throws Exception {
        TemporadaResponse temporada = new TemporadaResponse(1, "2025-2026", LocalDate.of(2025, 9, 1), LocalDate.of(2026, 8, 31));
        when(temporadaService.findById(1)).thenReturn(Optional.of(temporada));

        mockMvc.perform(get("/temporadas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("2025-2026"))
                .andExpect(jsonPath("$.fechaInicio").value("01/09/2025"))
                .andExpect(jsonPath("$.fechaFin").value("31/08/2026"));
    }

    @Test
    @WithMockUser
    void testGetById_Authenticated_NotFound() throws Exception {
        when(temporadaService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/temporadas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetById_Unauthenticated() throws Exception {
        mockMvc.perform(get("/temporadas/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate_Admin_Success() throws Exception {
        TemporadaRequest request = new TemporadaRequest("2025-2026", LocalDate.of(2025, 9, 1), LocalDate.of(2026, 8, 31));
        TemporadaResponse response = new TemporadaResponse(1, "2025-2026", LocalDate.of(2025, 9, 1), LocalDate.of(2026, 8, 31));
        when(temporadaService.save(any(TemporadaRequest.class))).thenReturn(response);

        mockMvc.perform(post("/temporadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api-v0/temporadas/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("2025-2026"))
                .andExpect(jsonPath("$.fechaInicio").value("01/09/2025"))
                .andExpect(jsonPath("$.fechaFin").value("31/08/2026"));
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testCreate_Arbitro_Forbidden() throws Exception {
        TemporadaRequest request = new TemporadaRequest("2025-2026", LocalDate.of(2025, 9, 1), LocalDate.of(2026, 8, 31));

        mockMvc.perform(post("/temporadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate_InvalidData() throws Exception {
        TemporadaRequest request = new TemporadaRequest("", null, null);

        mockMvc.perform(post("/temporadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_Admin_Success() throws Exception {
        TemporadaRequest request = new TemporadaRequest("2026-2027", LocalDate.of(2026, 9, 1), LocalDate.of(2027, 8, 31));
        Temporada temporada = new Temporada("2025-2026", LocalDate.of(2025, 9, 1), LocalDate.of(2026, 8, 31));
        TemporadaResponse response = new TemporadaResponse(1, "2026-2027", LocalDate.of(2026, 9, 1), LocalDate.of(2027, 8, 31));
        when(temporadaService.findEntityById(1)).thenReturn(Optional.of(temporada));
        when(temporadaService.update(any(TemporadaRequest.class), any(Optional.class))).thenReturn(response);

        mockMvc.perform(put("/temporadas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("2026-2027"))
                .andExpect(jsonPath("$.fechaInicio").value("01/09/2026"))
                .andExpect(jsonPath("$.fechaFin").value("31/08/2027"));
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testUpdate_Arbitro_Forbidden() throws Exception {
        TemporadaRequest request = new TemporadaRequest("2026-2027", LocalDate.of(2026, 9, 1), LocalDate.of(2027, 8, 31));

        mockMvc.perform(put("/temporadas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_Admin_Success() throws Exception {
        Temporada temporada = new Temporada("2025-2026", LocalDate.of(2025, 9, 1), LocalDate.of(2026, 8, 31));
        when(temporadaService.findEntityById(1)).thenReturn(Optional.of(temporada));
        doNothing().when(temporadaService).delete(1);

        mockMvc.perform(delete("/temporadas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testDelete_Arbitro_Forbidden() throws Exception {
        mockMvc.perform(delete("/temporadas/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_NotFound() throws Exception {
        when(temporadaService.findEntityById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/temporadas/1"))
                .andExpect(status().isNotFound());
    }
}
