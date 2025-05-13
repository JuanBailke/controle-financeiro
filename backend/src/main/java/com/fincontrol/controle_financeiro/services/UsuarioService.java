package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.models.Usuario;
import com.fincontrol.controle_financeiro.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario registrar(Usuario usuario){
        return repository.save(usuario);
    }

    public Usuario autenticar(String email, String senha){
        return repository.findByEmail(email);
    }

    public Usuario findById(Integer id) {
        Optional<Usuario> usuario = repository.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }
}
