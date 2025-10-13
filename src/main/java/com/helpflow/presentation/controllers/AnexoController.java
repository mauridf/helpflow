package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.AnexoDTO;
import com.helpflow.application.services.AnexoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/anexos")
@RequiredArgsConstructor
public class AnexoController {

    private final AnexoService anexoService;

    @GetMapping("/mensagem/{mensagemId}")
    public ResponseEntity<List<AnexoDTO>> listarPorMensagem(@PathVariable String mensagemId) {
        List<AnexoDTO> anexos = anexoService.listarPorMensagem(mensagemId);
        return ResponseEntity.ok(anexos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnexoDTO> buscarPorId(@PathVariable String id) {
        AnexoDTO anexo = anexoService.buscarPorId(id);
        return ResponseEntity.ok(anexo);
    }

    @PostMapping("/mensagem/{mensagemId}")
    public ResponseEntity<AnexoDTO> criar(
            @PathVariable String mensagemId,
            @RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        AnexoDTO anexoCriado = anexoService.criar(mensagemId, arquivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(anexoCriado);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable String id) throws IOException {
        AnexoDTO anexo = anexoService.buscarPorId(id);
        byte[] arquivo = anexoService.downloadArquivo(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", anexo.getNomeArquivo());
        headers.setContentLength(arquivo.length);

        return new ResponseEntity<>(arquivo, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) throws IOException {
        anexoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}