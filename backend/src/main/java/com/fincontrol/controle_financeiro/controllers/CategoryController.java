package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.Category;
import com.fincontrol.controle_financeiro.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = service.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/register")
    public ResponseEntity<Category> register(@RequestBody Category category) {
        return ResponseEntity.ok(service.register(category));
    }
}
