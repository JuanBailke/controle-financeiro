package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.models.transaction.Transaction;
import com.fincontrol.controle_financeiro.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public Transaction registerTransaction(Transaction transaction){
        return repository.save(transaction);
    }

    public List<Transaction> findByUserId(Integer usuarioId) {
        return repository.findByUserId(usuarioId);
    }
}
