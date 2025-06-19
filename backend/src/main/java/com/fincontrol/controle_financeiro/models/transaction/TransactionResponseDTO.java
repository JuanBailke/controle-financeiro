package com.fincontrol.controle_financeiro.models.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransactionResponseDTO {

    private String description;
    private BigDecimal value;
    private LocalDate date;
    private TransactionType type;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TransactionResponseDTO fromEntity(Transaction transaction) {
        System.out.println("Iniciado fromEntity");
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setDescription(transaction.getDescription());
        response.setValue(transaction.getValue());
        response.setDate(transaction.getDate());
        response.setType(transaction.getTransactionType());
        response.setCategory(transaction.getCategory().getName());
        response.setCreatedAt(transaction.getCreatedAt());
        response.setUpdatedAt(transaction.getUpdatedAt());
        System.out.println("Finalizado fromEntity");
        return response;
    }

}
