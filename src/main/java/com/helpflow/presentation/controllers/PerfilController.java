package com.helpflow.presentation.controllers;

import com.helpflow.domain.entities.Perfil;
import com.helpflow.infrastructure.persistence.mongodb.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilRepository perfilRepository;

    @GetMapping
    public ResponseEntity<List<Perfil>> listarTodos() {
        List<Perfil> perfis = perfilRepository.findAll();
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> buscarPorId(@PathVariable String id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Perfil> buscarPorNome(@PathVariable String nome) {
        Perfil perfil = perfilRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return ResponseEntity.ok(perfil);
    }
}