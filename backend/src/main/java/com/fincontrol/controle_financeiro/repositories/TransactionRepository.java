package com.fincontrol.controle_financeiro.repositories;

import com.fincontrol.controle_financeiro.models.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    List<Transacao> findByUsuarioId(Integer usuarioId);
}
