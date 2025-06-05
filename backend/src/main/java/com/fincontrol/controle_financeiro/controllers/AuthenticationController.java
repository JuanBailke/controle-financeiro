package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.repositories.UserRepository;
import com.fincontrol.controle_financeiro.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        Optional<User> usuarioOpt = repository.findByEmail(email);
        if (usuarioOpt.isPresent()){
            User user = usuarioOpt.get();
            if (passwordEncoder.matches(senha, user.getPassword())) {
                String token = jwtUtil.generateToken(email);
                return ResponseEntity.ok().body(token);
            } else {
                return ResponseEntity.status(401).body("Senha incorreta");
            }
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }
}
