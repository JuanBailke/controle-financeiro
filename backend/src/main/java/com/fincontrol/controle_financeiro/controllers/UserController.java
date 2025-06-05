package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.Usuario;
import com.fincontrol.controle_financeiro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> insert(@RequestBody Usuario obj){
        return ResponseEntity.ok(service.registrar(obj));
    }

}
