package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.models.Categoria;
import com.fincontrol.controle_financeiro.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria registrar(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Categoria findByNome(String nome) {
        return repository.findByNome(nome);
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }
}
