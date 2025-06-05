package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.services.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class FinancialReportController {
    @Autowired
    private FinancialReportService service;

    @GetMapping("/monthly")
    public ResponseEntity<String> generateMonthlyReport(){
        service.generateMonthlyReport();
        return ResponseEntity.ok("Relatório mensal gerado com sucesso");
    }

    @GetMapping("/yearly")
    public ResponseEntity<String> generateAnnualReport(){
        service.generateAnnualReport();
        return ResponseEntity.ok("Relatório anual gerado com sucesso");
    }
}
