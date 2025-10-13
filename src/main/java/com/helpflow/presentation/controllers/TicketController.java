package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.TicketDTO;
import com.helpflow.application.dtos.CriarTicketDTO;
import com.helpflow.application.dtos.AtualizarTicketDTO;
import com.helpflow.application.services.TicketService;
import com.helpflow.domain.enums.StatusTicket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> listarTodos() {
        List<TicketDTO> tickets = ticketService.listarTodos();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> buscarPorId(@PathVariable String id) {
        TicketDTO ticket = ticketService.buscarPorId(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<TicketDTO> buscarPorCodigo(@PathVariable String codigo) {
        TicketDTO ticket = ticketService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public ResponseEntity<TicketDTO> criar(@RequestBody CriarTicketDTO dto, @RequestParam String clienteId) {
        TicketDTO ticketCriado = ticketService.criar(dto, clienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> atualizar(@PathVariable String id, @RequestBody AtualizarTicketDTO dto) {
        TicketDTO ticketAtualizado = ticketService.atualizar(id, dto);
        return ResponseEntity.ok(ticketAtualizado);
    }

    @PatchMapping("/{id}/atribuir-atendente")
    public ResponseEntity<TicketDTO> atribuirAtendente(@PathVariable String id, @RequestParam String atendenteId) {
        TicketDTO ticketAtualizado = ticketService.atribuirAtendente(id, atendenteId);
        return ResponseEntity.ok(ticketAtualizado);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketDTO> alterarStatus(@PathVariable String id, @RequestParam StatusTicket status) {
        TicketDTO ticketAtualizado = ticketService.alterarStatus(id, status);
        return ResponseEntity.ok(ticketAtualizado);
    }

    @PatchMapping("/{id}/adicionar-tempo")
    public ResponseEntity<TicketDTO> adicionarTempoGasto(@PathVariable String id, @RequestParam Integer minutos) {
        TicketDTO ticketAtualizado = ticketService.adicionarTempoGasto(id, minutos);
        return ResponseEntity.ok(ticketAtualizado);
    }

    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<TicketDTO> avaliar(@PathVariable String id, @RequestParam Integer nota,
                                             @RequestParam(required = false) String feedback) {
        TicketDTO ticketAtualizado = ticketService.avaliar(id, nota, feedback);
        return ResponseEntity.ok(ticketAtualizado);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<TicketDTO>> listarPorCliente(@PathVariable String clienteId) {
        List<TicketDTO> tickets = ticketService.listarPorCliente(clienteId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/atendente/{atendenteId}")
    public ResponseEntity<List<TicketDTO>> listarPorAtendente(@PathVariable String atendenteId) {
        List<TicketDTO> tickets = ticketService.listarPorAtendente(atendenteId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketDTO>> listarPorStatus(@PathVariable StatusTicket status) {
        List<TicketDTO> tickets = ticketService.listarPorStatus(status);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<TicketDTO>> listarAtrasados() {
        List<TicketDTO> tickets = ticketService.listarAtrasados();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/departamento/{departamentoId}")
    public ResponseEntity<List<TicketDTO>> listarPorDepartamento(@PathVariable String departamentoId) {
        List<TicketDTO> tickets = ticketService.listarPorDepartamento(departamentoId);
        return ResponseEntity.ok(tickets);
    }
}