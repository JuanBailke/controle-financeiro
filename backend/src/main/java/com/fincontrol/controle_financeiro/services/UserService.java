package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.models.Usuario;
import com.fincontrol.controle_financeiro.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario registrar(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repository.save(usuario);
    }

    public Usuario autenticar(String email, String senha){
        Optional<Usuario> usuario = repository.findByEmail(email);
        return usuario.map(u -> {
            if (passwordEncoder.matches(senha, u.getSenha())) {
                return u;
            } else {
                throw new RuntimeException("Senha incorreta");
            }
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario findById(Integer id) {
        Optional<Usuario> usuario = repository.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }
}
