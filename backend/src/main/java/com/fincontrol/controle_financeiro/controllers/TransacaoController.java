package com.fincontrol.controle_financeiro.controllers;

import com.fincontrol.controle_financeiro.models.Transacao;
import com.fincontrol.controle_financeiro.services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @Autowired
    private TransacaoService service;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Transacao>> findByUsuarioId(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Transacao> registrar(@RequestBody Transacao transacao) {
        return ResponseEntity.ok(service.registrarTransacao(transacao));
    }

}
