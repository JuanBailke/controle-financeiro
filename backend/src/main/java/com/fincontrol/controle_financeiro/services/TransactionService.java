package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.models.Transacao;
import com.fincontrol.controle_financeiro.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public Transacao registrarTransacao(Transacao transacao){
        return repository.save(transacao);
    }

    public List<Transacao> findByUsuarioId(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
