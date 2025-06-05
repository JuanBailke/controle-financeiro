package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.models.user.UserRequestedDTO;
import com.fincontrol.controle_financeiro.repositories.UserRepository;
import com.fincontrol.controle_financeiro.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestedDTO userRequestedDTO) {

        Optional<User> usuarioOpt = repository.findByEmail(userRequestedDTO.email());
        if (usuarioOpt.isPresent()){
            User user = usuarioOpt.get();
            if (passwordEncoder.matches(userRequestedDTO.password(), user.getPassword())) {
                String token = jwtUtil.generateToken(userRequestedDTO.email());
                return ResponseEntity.ok().body(token);
            } else {
                return ResponseEntity.status(401).body("Senha incorreta");
            }
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }
}
