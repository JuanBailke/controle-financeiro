package com.fincontrol.controle_financeiro.repositories;

import com.fincontrol.controle_financeiro.models.FinancialReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialReportRepository extends JpaRepository<FinancialReport, Integer> {
}
