package com.fincontrol.controle_financeiro.repositories;

import com.fincontrol.controle_financeiro.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Categoria findByNome(String nome);
}
