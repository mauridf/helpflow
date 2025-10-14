package com.helpflow.presentation.controllers;

import com.helpflow.domain.entities.SLA;
import com.helpflow.infrastructure.persistence.mongodb.SLARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slas")
@RequiredArgsConstructor
public class SLAController {

    private final SLARepository slaRepository;

    @GetMapping
    public ResponseEntity<List<SLA>> listarTodos() {
        List<SLA> slas = slaRepository.findAll();
        return ResponseEntity.ok(slas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SLA> buscarPorId(@PathVariable String id) {
        SLA sla = slaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SLA não encontrado"));
        return ResponseEntity.ok(sla);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<SLA> buscarPorNome(@PathVariable String nome) {
        SLA sla = slaRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("SLA não encontrado"));
        return ResponseEntity.ok(sla);
    }
}