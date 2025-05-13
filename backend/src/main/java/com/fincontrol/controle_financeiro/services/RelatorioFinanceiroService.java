package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.repositories.RelatorioFinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioFinanceiroService {

    @Autowired
    private RelatorioFinanceiroRepository repository;

    public void gerarRelatorioMensal(){}

    public void gerarRelatorioAnual(){}
}
