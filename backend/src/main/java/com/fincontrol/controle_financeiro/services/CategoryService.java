package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.models.Category;
import com.fincontrol.controle_financeiro.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category register(Category category) {
        return repository.save(category);
    }

    public Category findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Category findByName(String nome) {
        return repository.findByName(nome);
    }

    public List<Category> findAll() {
        return repository.findAll();
    }
}
