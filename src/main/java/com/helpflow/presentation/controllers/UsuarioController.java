package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.UsuarioDTO;
import com.helpflow.application.dtos.CriarUsuarioDTO;
import com.helpflow.application.dtos.AtualizarUsuarioDTO;
import com.helpflow.application.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable String id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@PathVariable String email) {
        UsuarioDTO usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody CriarUsuarioDTO dto) {
        UsuarioDTO usuarioCriado = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable String id, @RequestBody AtualizarUsuarioDTO dto) {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable String id) {
        usuarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable String id) {
        usuarioService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/perfil/{perfilNome}")
    public ResponseEntity<List<UsuarioDTO>> listarPorPerfil(@PathVariable String perfilNome) {
        List<UsuarioDTO> usuarios = usuarioService.listarPorPerfil(perfilNome);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/atendentes")
    public ResponseEntity<List<UsuarioDTO>> listarAtendentes() {
        List<UsuarioDTO> atendentes = usuarioService.listarAtendentes();
        return ResponseEntity.ok(atendentes);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioDTO>> listarClientes() {
        List<UsuarioDTO> clientes = usuarioService.listarClientes();
        return ResponseEntity.ok(clientes);
    }
}