package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.HistoricoTicketDTO;
import com.helpflow.application.dtos.CriarHistoricoTicketDTO;
import com.helpflow.application.services.HistoricoTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico-tickets")
@RequiredArgsConstructor
public class HistoricoTicketController {

    private final HistoricoTicketService historicoTicketService;

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<HistoricoTicketDTO>> listarPorTicket(@PathVariable String ticketId) {
        List<HistoricoTicketDTO> historico = historicoTicketService.listarPorTicket(ticketId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistoricoTicketDTO>> listarPorUsuario(@PathVariable String usuarioId) {
        List<HistoricoTicketDTO> historico = historicoTicketService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/acao/{acao}")
    public ResponseEntity<List<HistoricoTicketDTO>> listarPorAcao(@PathVariable String acao) {
        List<HistoricoTicketDTO> historico = historicoTicketService.listarPorAcao(acao);
        return ResponseEntity.ok(historico);
    }

    @PostMapping
    public ResponseEntity<HistoricoTicketDTO> criar(@RequestBody CriarHistoricoTicketDTO dto) {
        HistoricoTicketDTO historicoCriado = historicoTicketService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(historicoCriado);
    }
}