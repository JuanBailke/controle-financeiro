package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.transaction.Transaction;
import com.fincontrol.controle_financeiro.models.transaction.TransactionRequestDTO;
import com.fincontrol.controle_financeiro.models.transaction.TransactionResponseDTO;
import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.security.MyUserDetails;
import com.fincontrol.controle_financeiro.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public ResponseEntity<List<TransactionResponseDTO>> findByUsuarioId(@AuthenticationPrincipal MyUserDetails userDetails) {
        System.out.println("Iniciado controller");
        User user = userDetails.getUser();
//        List<Transaction> list = service.findByUser(user);
//        System.out.println("Executado findByUser");
//        List<TransactionResponseDTO> response = list.stream().map(TransactionResponseDTO::fromEntity).toList();
//        return ResponseEntity.ok(response);
        return ResponseEntity.ok(service.findByUser(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{userId}/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getUserTransactions(@PathVariable Integer userId){
        List<Transaction> list = service.findByUserId(userId);
        List<TransactionResponseDTO> response = list.stream().map(TransactionResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TransactionRequestDTO request, @AuthenticationPrincipal MyUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user == null) {
            return ResponseEntity.status(401).body("Usuário não encontrado"); // Unauthorized
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerTransaction(request, user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TransactionRequestDTO request, @AuthenticationPrincipal MyUserDetails userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(service.updateTransaction(id, request, user));
    }

}
