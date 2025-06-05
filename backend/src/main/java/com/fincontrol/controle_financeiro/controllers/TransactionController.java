package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.transaction.Transaction;
import com.fincontrol.controle_financeiro.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> findByUsuarioId(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.findByUserId(usuarioId));
    }

    @PostMapping("/register")
    public ResponseEntity<Transaction> register(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(service.registerTransaction(transaction));
    }

}
