package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.MensagemDTO;
import com.helpflow.application.dtos.CriarMensagemDTO;
import com.helpflow.application.dtos.AtualizarMensagemDTO;
import com.helpflow.application.services.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<MensagemDTO>> listarPorTicket(@PathVariable String ticketId) {
        List<MensagemDTO> mensagens = mensagemService.listarPorTicket(ticketId);
        return ResponseEntity.ok(mensagens);
    }

    @GetMapping("/ticket/{ticketId}/publicas")
    public ResponseEntity<List<MensagemDTO>> listarPublicasPorTicket(@PathVariable String ticketId) {
        List<MensagemDTO> mensagens = mensagemService.listarPublicasPorTicket(ticketId);
        return ResponseEntity.ok(mensagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensagemDTO> buscarPorId(@PathVariable String id) {
        MensagemDTO mensagem = mensagemService.buscarPorId(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/ticket/{ticketId}/nao-lidas")
    public ResponseEntity<Long> contarNaoLidas(@PathVariable String ticketId, @RequestParam String usuarioId) {
        Long count = mensagemService.contarMensagensNaoLidas(ticketId, usuarioId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/ticket/{ticketId}/usuario/{usuarioId}")
    public ResponseEntity<MensagemDTO> criar(
            @PathVariable String ticketId,
            @PathVariable String usuarioId,
            @RequestBody CriarMensagemDTO dto) {
        MensagemDTO mensagemCriada = mensagemService.criar(dto, ticketId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagemCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> atualizar(@PathVariable String id, @RequestBody AtualizarMensagemDTO dto) {
        MensagemDTO mensagemAtualizada = mensagemService.atualizar(id, dto);
        return ResponseEntity.ok(mensagemAtualizada);
    }

    @PatchMapping("/{id}/marcar-lida")
    public ResponseEntity<Void> marcarComoLida(@PathVariable String id) {
        mensagemService.marcarComoLida(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/ticket/{ticketId}/marcar-todas-lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(@PathVariable String ticketId, @RequestParam String usuarioId) {
        mensagemService.marcarTodasComoLidas(ticketId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        mensagemService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}