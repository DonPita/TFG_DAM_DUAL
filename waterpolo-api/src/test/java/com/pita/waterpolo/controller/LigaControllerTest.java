package com.pita.waterpolo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pita.waterpolo.dto.request.LigaRequest;
import com.pita.waterpolo.dto.response.LigaResponse;
import com.pita.waterpolo.entity.Liga;
import com.pita.waterpolo.service.LigaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LigaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LigaService ligaService;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    void testGetAll_Authenticated_Empty() throws Exception {
        when(ligaService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/ligas"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAll_Authenticated() throws Exception {
        LigaResponse ligaResponse = new LigaResponse(1, "Liga 1", true);
        when(ligaService.findAll()).thenReturn(List.of(ligaResponse));

        mockMvc.perform(get("/ligas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Liga 1"))
                .andExpect(jsonPath("$[0].activo").value(true));
    }

    @Test
    void testGetAll_Unauthenticated() throws Exception {
        mockMvc.perform(get("/ligas"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate_Admin_Success() throws Exception {
        LigaRequest request = new LigaRequest("Liga Absoluta Masculina", true);
        LigaResponse response = new LigaResponse(1, "Liga Absoluta Masculina", true);
        when(ligaService.save(any(LigaRequest.class))).thenReturn(response);

        mockMvc.perform(post("/ligas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api-v0/ligas/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Liga Absoluta Masculina"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testCreate_Arbitro_Forbidden() throws Exception {
        LigaRequest request = new LigaRequest("Liga Absoluta Masculina", true);

        mockMvc.perform(post("/ligas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate_DuplicateName() throws Exception {
        LigaRequest request = new LigaRequest("Liga Absoluta Masculina", true);
        when(ligaService.save(any(LigaRequest.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry 'Liga Absoluta Masculina' for key 'nombre'"));

        mockMvc.perform(post("/ligas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Ya existe un registro con los datos proporcionados"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_Admin_Success() throws Exception {
        LigaRequest request = new LigaRequest("Liga Absoluta Femenina", false);
        Liga liga = new Liga(1, "Liga Absoluta Masculina", true);
        LigaResponse response = new LigaResponse(1, "Liga Absoluta Femenina", false);
        when(ligaService.findEntityById(1)).thenReturn(Optional.of(liga));
        when(ligaService.update(any(LigaRequest.class), any(Optional.class))).thenReturn(response);

        mockMvc.perform(put("/ligas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Liga Absoluta Femenina"))
                .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testUpdate_Arbitro_Forbidden() throws Exception {
        LigaRequest request = new LigaRequest("Liga Absoluta Femenina", false);

        mockMvc.perform(put("/ligas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_Admin_Success() throws Exception {
        Liga liga = new Liga(1, "Liga Absoluta Masculina", true);
        when(ligaService.findEntityById(1)).thenReturn(Optional.of(liga));
        doNothing().when(ligaService).delete(1);

        mockMvc.perform(delete("/ligas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testDelete_Arbitro_Forbidden() throws Exception {
        mockMvc.perform(delete("/ligas/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_NotFound() throws Exception {
        when(ligaService.findEntityById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/ligas/1"))
                .andExpect(status().isNotFound());
    }
}
