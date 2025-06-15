package com.tarefas.gerenciador_tarefas.controller;

import com.tarefas.gerenciador_tarefas.dto.AuthRequest;
import com.tarefas.gerenciador_tarefas.dto.AuthResponse;
import com.tarefas.gerenciador_tarefas.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Se autenticado com sucesso, gera o token
            String token = jwtUtil.generateToken(authRequest.getUsername());
            return new AuthResponse(token);

        } catch (AuthenticationException ex) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }
}
