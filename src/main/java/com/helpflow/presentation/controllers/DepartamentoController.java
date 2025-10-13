package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.DepartamentoDTO;
import com.helpflow.application.dtos.CriarDepartamentoDTO;
import com.helpflow.application.dtos.AtualizarDepartamentoDTO;
import com.helpflow.application.services.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> listarTodos() {
        List<DepartamentoDTO> departamentos = departamentoService.listarTodos();
        return ResponseEntity.ok(departamentos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<DepartamentoDTO>> listarAtivos() {
        List<DepartamentoDTO> departamentos = departamentoService.listarAtivos();
        return ResponseEntity.ok(departamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> buscarPorId(@PathVariable String id) {
        DepartamentoDTO departamento = departamentoService.buscarPorId(id);
        return ResponseEntity.ok(departamento);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<DepartamentoDTO> buscarPorNome(@PathVariable String nome) {
        DepartamentoDTO departamento = departamentoService.buscarPorNome(nome);
        return ResponseEntity.ok(departamento);
    }

    @PostMapping
    public ResponseEntity<DepartamentoDTO> criar(@RequestBody CriarDepartamentoDTO dto) {
        DepartamentoDTO departamentoCriado = departamentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> atualizar(@PathVariable String id, @RequestBody AtualizarDepartamentoDTO dto) {
        DepartamentoDTO departamentoAtualizado = departamentoService.atualizar(id, dto);
        return ResponseEntity.ok(departamentoAtualizado);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable String id) {
        departamentoService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable String id) {
        departamentoService.ativar(id);
        return ResponseEntity.noContent().build();
    }
}