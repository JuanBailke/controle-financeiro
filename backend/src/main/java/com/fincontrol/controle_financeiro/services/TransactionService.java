package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.models.Category;
import com.fincontrol.controle_financeiro.models.transaction.Transaction;
import com.fincontrol.controle_financeiro.models.transaction.TransactionRequestDTO;
import com.fincontrol.controle_financeiro.models.transaction.TransactionResponseDTO;
import com.fincontrol.controle_financeiro.models.transaction.TransactionType;
import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.repositories.CategoryRepository;
import com.fincontrol.controle_financeiro.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Transaction registerTransaction(TransactionRequestDTO transactionRequest, User user) {
        validateTransaction(transactionRequest);

        Transaction transaction = new Transaction();
        transaction.setDescription(transactionRequest.description());
        transaction.setValue(transactionRequest.value());
        transaction.setDate(transactionRequest.date());
        transaction.setTransactionType(transactionRequest.type());
        transaction.setUser(user);

        Category category = categoryRepository.findByName(transactionRequest.category())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        transaction.setCategory(category);

        return repository.save(transaction);
    }

    @Transactional
    public Transaction updateTransaction(Integer id, TransactionRequestDTO transactionRequest, User user) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Usuário não autorizado a atualizar esta transação");
        }
        validateTransaction(transactionRequest);
        transaction.setDescription(transactionRequest.description());
        transaction.setValue(transactionRequest.value());
        transaction.setDate(transactionRequest.date());
        transaction.setTransactionType(transactionRequest.type());
        Category category = categoryRepository.findByName(transactionRequest.category())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        transaction.setCategory(category);

        return repository.save(transaction);
    }


    public List<Transaction> findByUserAndType(Integer userId, TransactionType type) {
        return repository.findByUserIdAndTransactionType(userId, type.name());
    }

    public List<Transaction> findByUserAndCategory(Integer userId, Long categoryId) {
        return repository.findByUserIdAndCategoryId(userId, categoryId);
    }

    public List<TransactionResponseDTO> findByUser(User user) {
        List<Transaction> list = repository.findByUserId(user.getId());
        return list.stream().map(TransactionResponseDTO::fromEntity).toList();
    }

    public List<Transaction> findByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    private void validateTransaction(TransactionRequestDTO transaction) {
        if (transaction.value().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Não é possível registrar uma transação com valor negativo.");
        }
        if (transaction.value().compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("O valor da transação não pode ser zero.");
        }
        if (transaction.date() == null) {
            throw new RuntimeException("A data da transação não pode ser nula.");
        }
        if (transaction.description() == null || transaction.description().isEmpty()) {
            throw new RuntimeException("A descrição da transação não pode ser nula ou vazia.");
        }
        if (transaction.type() == null) {
            throw new RuntimeException("O tipo da transação não pode ser nulo.");
        }
        if (transaction.category() == null || transaction.category().isEmpty()) {
            throw new RuntimeException("A categoria da transação não pode ser nula ou vazia.");
        }
    }
}
