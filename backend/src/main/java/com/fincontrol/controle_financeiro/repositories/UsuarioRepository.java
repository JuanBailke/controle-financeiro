package com.fincontrol.controle_financeiro.repositories;

import com.fincontrol.controle_financeiro.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);
}
