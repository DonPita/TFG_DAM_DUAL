package com.pita.waterpolo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pita.waterpolo.dto.request.EquipoRequest;
import com.pita.waterpolo.dto.response.EquipoResponse;
import com.pita.waterpolo.entity.Equipo;
import com.pita.waterpolo.service.EquipoService;
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
public class EquipoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EquipoService equipoService;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
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
        when(equipoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/equipos"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAll_Authenticated_WithEquipos() throws Exception {
        EquipoResponse equipo = new EquipoResponse(1, "Equipo A", "A", LocalDate.of(2018, 1,1 ));
        when(equipoService.findAll()).thenReturn(List.of(equipo));

        mockMvc.perform(get("/equipos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].nombre").value("Equipo A"))
                .andExpect(jsonPath("$.[0].ciudad").value("A"))
                .andExpect(jsonPath("$.[0].fechaFundacion").value("01/01/2018"));
    }

    @Test
    void testGetAll_Unauthenticated() throws Exception {
        mockMvc.perform(get("/equipos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void testGetById_Authenticated_Success() throws Exception {
        EquipoResponse equipo = new EquipoResponse(1, "Equipo A", "A", LocalDate.of(2018, 1,1 ));
        when(equipoService.findById(1)).thenReturn(Optional.of(equipo));

        mockMvc.perform(get("/equipos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Equipo A"))
                .andExpect(jsonPath("$.ciudad").value("A"))
                .andExpect(jsonPath("$.fechaFundacion").value("01/01/2018"));
    }

    @Test
    @WithMockUser
    void testGetById_Authenticated_NotFound() throws Exception {
        when(equipoService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/equipos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetById_Unauthenticated() throws Exception {
        mockMvc.perform(get("/equipos/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate_Admin_Success() throws Exception {
        EquipoRequest request = new EquipoRequest("Club Waterpolo Lugo", "Lugo", LocalDate.of(2018, 9, 1));
        EquipoResponse response = new EquipoResponse(1, "Club Waterpolo Lugo", "Lugo", LocalDate.of(2018, 9, 1));
        when(equipoService.save(any(EquipoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/equipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api-v0/equipos/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Club Waterpolo Lugo"))
                .andExpect(jsonPath("$.ciudad").value("Lugo"))
                .andExpect(jsonPath("$.fechaFundacion").value("01/09/2018"));
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testCreate_Arbitro_Forbidden() throws Exception {
        EquipoRequest request = new EquipoRequest("Club Waterpolo Lugo", "Lugo", LocalDate.of(2018, 9, 1));

        mockMvc.perform(post("/equipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate_InvalidData() throws Exception {
        EquipoRequest request = new EquipoRequest("", "", null);

        mockMvc.perform(post("/equipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate_DuplicateName() throws Exception {
        EquipoRequest request = new EquipoRequest("Club Waterpolo Lugo", "Lugo", LocalDate.of(2018, 9, 1));
        when(equipoService.save(any(EquipoRequest.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry 'Club Waterpolo Lugo' for key 'nombre'"));

        mockMvc.perform(post("/equipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Ya existe un registro con los datos proporcionados"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_Admin_Success() throws Exception {
        EquipoRequest request = new EquipoRequest("Club Waterpolo Vigo", "Vigo", LocalDate.of(2019, 10, 1));
        Equipo equipo = new Equipo(1, "Club Waterpolo Lugo", "Lugo", LocalDate.of(2018, 9, 1));
        EquipoResponse response = new EquipoResponse(1, "Club Waterpolo Vigo", "Vigo", LocalDate.of(2019, 10, 1));
        when(equipoService.findEntityById(1)).thenReturn(Optional.of(equipo));
        when(equipoService.update(any(EquipoRequest.class), any(Optional.class))).thenReturn(response);

        mockMvc.perform(put("/equipos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Club Waterpolo Vigo"))
                .andExpect(jsonPath("$.ciudad").value("Vigo"))
                .andExpect(jsonPath("$.fechaFundacion").value("01/10/2019"));
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testUpdate_Arbitro_Forbidden() throws Exception {
        EquipoRequest request = new EquipoRequest("Club Waterpolo Vigo", "Vigo", LocalDate.of(2019, 10, 1));

        mockMvc.perform(put("/equipos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_NotFound() throws Exception {
        EquipoRequest request = new EquipoRequest("Club Waterpolo Vigo", "Vigo", LocalDate.of(2019, 10, 1));
        when(equipoService.findEntityById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/equipos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_Admin_Success() throws Exception {
        Equipo equipo = new Equipo(1, "Club Waterpolo Lugo", "Lugo", LocalDate.of(2018, 9, 1));
        when(equipoService.findEntityById(1)).thenReturn(Optional.of(equipo));
        doNothing().when(equipoService).delete(1);

        mockMvc.perform(delete("/equipos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testDelete_Arbitro_Forbidden() throws Exception {
        mockMvc.perform(delete("/equipos/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_NotFound() throws Exception {
        when(equipoService.findEntityById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/equipos/1"))
                .andExpect(status().isNotFound());
    }
}
