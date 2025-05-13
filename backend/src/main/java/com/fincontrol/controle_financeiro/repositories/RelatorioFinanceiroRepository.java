package com.fincontrol.controle_financeiro.repositories;

import com.fincontrol.controle_financeiro.models.RelatorioFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatorioFinanceiroRepository extends JpaRepository<RelatorioFinanceiro, Integer> {
}
