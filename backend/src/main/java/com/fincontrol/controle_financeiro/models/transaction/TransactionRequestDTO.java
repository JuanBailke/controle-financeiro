package com.fincontrol.controle_financeiro.models.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequestDTO(String description, BigDecimal value, LocalDate date, TransactionType type, Integer userId, String category) {
}
