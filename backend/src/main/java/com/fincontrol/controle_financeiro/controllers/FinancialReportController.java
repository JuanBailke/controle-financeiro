package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.RelatorioFinanceiro;
import com.fincontrol.controle_financeiro.services.RelatorioFinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RelatorioFinanceiroController {
    @Autowired
    private RelatorioFinanceiroService service;

    @GetMapping("/mensal")
    public ResponseEntity<String> gerarRelatorioMensal(){
        service.gerarRelatorioMensal();
        return ResponseEntity.ok("Relatório mensal gerado com sucesso");
    }

    @GetMapping("/anual")
    public ResponseEntity<String> gerarRelatorioAnual(){
        service.gerarRelatorioAnual();
        return ResponseEntity.ok("Relatório anual gerado com sucesso");
    }
}
