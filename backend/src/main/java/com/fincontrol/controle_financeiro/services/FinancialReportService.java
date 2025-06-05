package com.fincontrol.controle_financeiro.services;

import com.fincontrol.controle_financeiro.repositories.FinancialReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialReportService {

    @Autowired
    private FinancialReportRepository repository;

    public void generateMonthlyReport(){}

    public void generateAnnualReport(){}
}
