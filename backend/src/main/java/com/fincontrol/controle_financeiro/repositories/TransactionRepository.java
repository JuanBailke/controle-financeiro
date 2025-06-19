package com.fincontrol.controle_financeiro.repositories;

import com.fincontrol.controle_financeiro.models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUserId(Integer userId);

    List<Transaction> findByUserIdAndTransactionType(Integer userId, String type);

    List<Transaction> findByUserIdAndCategoryId(Integer userId, Long categoryId);
}
