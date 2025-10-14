package com.helpflow.presentation.controllers;

import com.helpflow.domain.entities.Prioridade;
import com.helpflow.infrastructure.persistence.mongodb.PrioridadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prioridades")
@RequiredArgsConstructor
public class PrioridadeController {

    private final PrioridadeRepository prioridadeRepository;

    @GetMapping
    public ResponseEntity<List<Prioridade>> listarTodos() {
        List<Prioridade> prioridades = prioridadeRepository.findAll();
        return ResponseEntity.ok(prioridades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prioridade> buscarPorId(@PathVariable String id) {
        Prioridade prioridade = prioridadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prioridade não encontrada"));
        return ResponseEntity.ok(prioridade);
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<Prioridade> buscarPorNivel(@PathVariable Integer nivel) {
        Prioridade prioridade = prioridadeRepository.findByNivel(nivel)
                .orElseThrow(() -> new RuntimeException("Prioridade não encontrada"));
        return ResponseEntity.ok(prioridade);
    }
}