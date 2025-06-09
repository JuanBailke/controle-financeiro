package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registrar(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User authenticate(String email, String password){
        Optional<User> user = repository.findByEmail(email);
        return user.map(u -> {
            if (passwordEncoder.matches(password, u.getPassword())) {
                return u;
            } else {
                throw new RuntimeException("Senha incorreta");
            }
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email)));
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
