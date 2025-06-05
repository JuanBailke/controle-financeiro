package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<User> insert(@RequestBody User obj){
        return ResponseEntity.ok(service.registrar(obj));
    }

}
