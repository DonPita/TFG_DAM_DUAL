package com.pita.auth_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pita.auth_service.dto.request.*;
import com.pita.auth_service.dto.response.AuthResponse;
import com.pita.auth_service.dto.response.TokenInfo;
import com.pita.auth_service.entity.Usuario;
import com.pita.auth_service.service.AuthService;
import com.pita.auth_service.service.JwtService;
import com.pita.auth_service.service.UsuarioService;
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


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private JwtService jwtService;

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
    void testRegister_Success() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@test.com",
                "Password123", "Test User");
        AuthResponse response = new AuthResponse("accessToken", "refreshToken");

        when(usuarioService.existsByNombreUsuario("testuser")).thenReturn(false);
        when(usuarioService.existsByEmail("test@test.com")).thenReturn(false);
        when(authService.register(any(Usuario.class))).thenReturn(response);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
    }

    @Test
    void testRegister_UsernameExists() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@test.com",
                "Password123", "Test User");

        when(usuarioService.existsByNombreUsuario("testuser")).thenReturn(true);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("USER_EXISTS"));
    }

    @Test
    void testRegister_EmailExists() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@test.com",
                "Password123", "Test User");

        when(usuarioService.existsByNombreUsuario("testuser")).thenReturn(false);
        when(usuarioService.existsByEmail("test@test.com")).thenReturn(true);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("EMAIL_EXISTS"));
    }

    @Test
    void testLogin_Success() throws Exception {
        LoginRequest request = new LoginRequest("test@test.com", "Password123");
        AuthResponse response = new AuthResponse("accessToken", "refreshToken");
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");

        when(usuarioService.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(authService.login(usuario, "Password123")).thenReturn(response);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
    }

    @Test
    void testLogin_UserNotFound() throws Exception {
        LoginRequest request = new LoginRequest("test@test.com", "Password123");

        when(usuarioService.findByEmail("test@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
    }

    @Test
    void testLogin_InvalidPassword() throws Exception {
        LoginRequest request = new LoginRequest("test@test.com", "WrongPassword");
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");

        when(usuarioService.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(authService.login(usuario, "WrongPassword")).thenThrow(new IllegalArgumentException("Contraseña incorrecta"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INVALID_PASSWORD"));
    }

    @Test
    void testValidateToken_Success() throws Exception {
        TokenRequest request = new TokenRequest("validToken");
        TokenInfo tokenInfo = new TokenInfo("testuser", "USUARIO");

        when(jwtService.validateAccessToken("validToken")).thenReturn(tokenInfo);

        mockMvc.perform(post("/auth/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("testuser"))
                .andExpect(jsonPath("$.rol").value("USUARIO"));
    }

    @Test
    void testValidateToken_Invalid() throws Exception {
        TokenRequest request = new TokenRequest("validToken");

        when(jwtService.validateAccessToken("validToken")).thenThrow(new IllegalArgumentException("Token inválido o expirado"));

        mockMvc.perform(post("/auth/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INVALID_TOKEN"));
    }

    @Test
    void testRefreshToken_Success() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("validRefreshToken");
        AuthResponse response = new AuthResponse("newAccessToken", "newRefreshToken");

        when(authService.refreshToken("validRefreshToken")).thenReturn(response);

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("newRefreshToken"));
    }

    @Test
    void testRefreshToken_Invalid() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("invalidRefreshToken");

        when(authService.refreshToken("invalidRefreshToken")).thenThrow(new IllegalArgumentException("RefreshToken no encontrado"));

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INVALID_REFRESH_TOKEN"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateMe_EmailExists() throws Exception {
        UpdateMeRequest request = new UpdateMeRequest("new@example.com", "New User", "New Name");
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testuser");
        usuario.setEmail("old@example.com");

        when(usuarioService.findByNombreUsuario("testuser")).thenReturn(Optional.of(usuario));
        when(usuarioService.existsByEmail("new@example.com")).thenReturn(true);

        mockMvc.perform(put("/auth/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //.with(csrf())) solo si es necesario usar csrf en SecurityConfig
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("EMAIL_EXISTS"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateMe_InvalidPassword() throws Exception {
        UpdateMeRequest request = new UpdateMeRequest("new@example.com", "New User", "invalid");
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testuser");
        usuario.setEmail("old@example.com");

        when(usuarioService.findByNombreUsuario("testuser")).thenReturn(Optional.of(usuario));
        when(usuarioService.existsByEmail("new@example.com")).thenReturn(false);
        when(authService.isValidPassword("invalid")).thenReturn(false);

        mockMvc.perform(put("/auth/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //.with(csrf())) solo si es necesario usar csrf en SecurityConfig
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_PASSWORD"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateMe_Success() throws Exception {
        UpdateMeRequest request = new UpdateMeRequest("test@test.com", "NewPassword123", "New User");
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setNombreCompleto("Old User");

        when(usuarioService.findByNombreUsuario("testuser")).thenReturn(Optional.of(usuario));
        when(usuarioService.existsByEmail("test@test.com")).thenReturn(false);
        when(authService.isValidPassword("NewPassword123")).thenReturn(true);
        when(passwordEncoder.encode("NewPassword123")).thenReturn("encodedPassword");

        doNothing().when(usuarioService).save(any(Usuario.class));

        mockMvc.perform(put("/auth/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //.with(csrf())) solo si es necesario usar csrf en SecurityConfig
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.nombreCompleto").value("New User"))
                .andExpect(jsonPath("$.contrasena").value("encodedPassword"));
    }

    @Test
    void testLogout_Success() throws Exception {
        LogoutRequest request = new LogoutRequest("validRefreshToken");

        doNothing().when(authService).logout("validRefreshToken");

        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testLogout_InvalidToken() throws Exception {
        LogoutRequest request = new LogoutRequest("invalidRefreshToken");

        doThrow(new IllegalArgumentException("RefreshToken no encontrado")).when(authService).logout("invalidRefreshToken");

        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REFRESH_TOKEN"));
    }
}