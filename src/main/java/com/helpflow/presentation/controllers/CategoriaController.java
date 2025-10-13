package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.CategoriaDTO;
import com.helpflow.application.dtos.CriarCategoriaDTO;
import com.helpflow.application.dtos.AtualizarCategoriaDTO;
import com.helpflow.application.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodos() {
        List<CategoriaDTO> categorias = categoriaService.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/departamento/{departamentoId}")
    public ResponseEntity<List<CategoriaDTO>> listarPorDepartamento(@PathVariable String departamentoId) {
        List<CategoriaDTO> categorias = categoriaService.listarPorDepartamento(departamentoId);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable String id) {
        CategoriaDTO categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<CategoriaDTO> buscarPorNome(@PathVariable String nome) {
        CategoriaDTO categoria = categoriaService.buscarPorNome(nome);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@RequestBody CriarCategoriaDTO dto) {
        CategoriaDTO categoriaCriada = categoriaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable String id, @RequestBody AtualizarCategoriaDTO dto) {
        CategoriaDTO categoriaAtualizada = categoriaService.atualizar(id, dto);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}