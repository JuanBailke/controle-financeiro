package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.Categoria;
import com.fincontrol.controle_financeiro.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll() {
        List<Categoria> categorias = service.findAll();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Categoria> registrar(Categoria categoria) {
        return ResponseEntity.ok(service.registrar(categoria));
    }
}
