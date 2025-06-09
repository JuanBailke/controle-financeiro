package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.security.JwtUtil;
import com.fincontrol.controle_financeiro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyUser(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        Optional<User> userOptional = service.findByEmail(email);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> insert(@RequestBody User obj){
        return ResponseEntity.ok(service.registrar(obj));
    }

}
