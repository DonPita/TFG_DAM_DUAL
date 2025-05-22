package com.pita.auth_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pita.auth_service.dto.request.UpdateUsuarioRequest;
import com.pita.auth_service.entity.Usuario;
import com.pita.auth_service.service.AuthService;
import com.pita.auth_service.service.UsuarioService;
import com.pita.auth_service.utils.Rol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {
    //.\mvnw test

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllAdmin_Empty() throws Exception {
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USUARIO")
    void testGetAllUsuario_Empty() throws Exception {
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "JUGADOR")
    void testGetAllJugador_Empty() throws Exception {
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ARBITRO")
    void testGetAllArbitro_Empty() throws Exception {
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAll_WithUsers() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreUsuario("testUser");
        usuario.setEmail("test@test.com");
        usuario.setNombreCompleto("Test User");
        usuario.setRol(Rol.USUARIO);

        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreUsuario").value("testUser"))
                .andExpect(jsonPath("$[0].email").value("test@test.com"))
                .andExpect(jsonPath("$[0].nombreCompleto").value("Test User"))
                .andExpect(jsonPath("$[0].rol").value("USUARIO"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetById_Success() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreUsuario("testuser");
        usuario.setEmail("test@test.com");
        usuario.setNombreCompleto("Test User");
        usuario.setRol(Rol.USUARIO);

        when(usuarioService.findById(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreUsuario").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.nombreCompleto").value("Test User"))
                .andExpect(jsonPath("$.rol").value("USUARIO"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetById_NotFound() throws Exception {
        when(usuarioService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.error").value("Usuario no encontrado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_Success() throws Exception {
        UpdateUsuarioRequest request = new UpdateUsuarioRequest("newuser", "new@test.com", "NewPassword123", "New User", "USUARIO");
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreUsuario("testuser");
        usuario.setEmail("test@test.com");
        usuario.setNombreCompleto("Test User");
        usuario.setRol(Rol.USUARIO);

        when(usuarioService.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioService.existsByNombreUsuario("newuser")).thenReturn(false);
        when(usuarioService.existsByEmail("new@test.com")).thenReturn(false);
        when(authService.isValidPassword("NewPassword123")).thenReturn(true);
        when(passwordEncoder.encode("NewPassword123")).thenReturn("encodedPassword");
        when(usuarioService.findAll()).thenReturn(List.of(usuario));
        doNothing().when(usuarioService).save(any(Usuario.class));

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //.with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("newuser"))
                .andExpect(jsonPath("$.email").value("new@test.com"))
                .andExpect(jsonPath("$.nombreCompleto").value("New User"))
                .andExpect(jsonPath("$.rol").value("USUARIO"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_UserNotFound() throws Exception {
        UpdateUsuarioRequest request = new UpdateUsuarioRequest("newuser", "new@test.com", "NewPassword123", "New User", "USUARIO");

        when(usuarioService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //.with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.error").value("Usuario no encontrado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_UsernameExists() throws Exception {
        UpdateUsuarioRequest request = new UpdateUsuarioRequest("newuser", "new@test.com", "NewPassword123", "New User", "USUARIO");
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreUsuario("testuser");
        usuario.setEmail("test@test.com");
        usuario.setRol(Rol.USUARIO);

        when(usuarioService.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioService.existsByNombreUsuario("newuser")).thenReturn(true);

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        //.with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("USER_EXISTS"))
                .andExpect(jsonPath("$.error").value("El nombre de usuario ya está en uso"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate_LastAdmin() throws Exception {
        UpdateUsuarioRequest request = new UpdateUsuarioRequest("newuser", "new@test.com", "NewPassword123", "New User", "USUARIO");
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreUsuario("testuser");
        usuario.setEmail("test@test.com");
        usuario.setRol(Rol.ADMIN);

        when(usuarioService.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioService.existsByNombreUsuario("newuser")).thenReturn(false);
        when(usuarioService.existsByEmail("new@test.com")).thenReturn(false);
        when(authService.isValidPassword("NewPassword123")).thenReturn(true);
        when(passwordEncoder.encode("NewPassword123")).thenReturn("encodedPassword");
        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        //.with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("LAST_ADMIN"))
                .andExpect(jsonPath("$.error").value("No se puede eliminar el último administrador"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_Success() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setRol(Rol.USUARIO);

        when(usuarioService.findById(1)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioService).deleteById(1);

        mockMvc.perform(delete("/usuarios/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_NotFound() throws Exception {
        when(usuarioService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/usuarios/1")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.error").value("Usuario no encontrado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete_LastAdmin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setRol(Rol.ADMIN);

        when(usuarioService.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(delete("/usuarios/1")
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("LAST_ADMIN"))
                .andExpect(jsonPath("$.error").value("No se puede eliminar el último administrador"));
    }
}

