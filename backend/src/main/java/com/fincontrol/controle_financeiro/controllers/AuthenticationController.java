package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.models.user.UserRequestDTO;
import com.fincontrol.controle_financeiro.repositories.UserRepository;
import com.fincontrol.controle_financeiro.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestDTO userRequestDTO) {

        Optional<User> usuarioOpt = repository.findByEmail(userRequestDTO.email());
        if (usuarioOpt.isPresent()){
            User user = usuarioOpt.get();
            if (passwordEncoder.matches(userRequestDTO.password(), user.getPassword())) {
                String accessToken = jwtUtil.generateAccessToken(userRequestDTO.email());
                String refreshToken = jwtUtil.generateRefreshToken(userRequestDTO.email());

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);

                return ResponseEntity.ok().body(tokens);
            } else {
                return ResponseEntity.status(401).body("Senha incorreta");
            }
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Token inválido ou não fornecido");
        }
        String refreshToken = token.replace("Bearer ", "");

        try {
            String email = jwtUtil.extractUsername(refreshToken);
            if (email != null && jwtUtil.validateToken(refreshToken)){
                String newAccessToken = jwtUtil.generateAccessToken(email);
                return ResponseEntity.ok(Collections.singletonMap("accessToken", newAccessToken));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("❌ Refresh token inválido ou expirado");
        }
        return ResponseEntity.status(400).body("❌ Erro ao renovar o token");
    }
}
