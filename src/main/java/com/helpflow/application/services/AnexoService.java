package com.helpflow.application.services;

import com.helpflow.application.dtos.AnexoDTO;
import com.helpflow.application.mappers.AnexoMapper;
import com.helpflow.domain.entities.Anexo;
import com.helpflow.domain.entities.Mensagem;
import com.helpflow.infrastructure.persistence.mongodb.AnexoRepository;
import com.helpflow.infrastructure.persistence.mongodb.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnexoService {

    private final AnexoRepository anexoRepository;
    private final MensagemRepository mensagemRepository;
    private final AnexoMapper anexoMapper;

    // Diretório para salvar arquivos (em produção, usar cloud storage)
    private final String UPLOAD_DIR = "uploads/";

    public List<AnexoDTO> listarPorMensagem(String mensagemId) {
        return anexoRepository.findByMensagemId(mensagemId).stream()
                .map(anexoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AnexoDTO buscarPorId(String id) {
        Anexo anexo = anexoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado com ID: " + id));
        return anexoMapper.toDTO(anexo);
    }

    @Transactional
    public AnexoDTO criar(String mensagemId, MultipartFile arquivo) throws IOException {
        Mensagem mensagem = mensagemRepository.findById(mensagemId)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));

        // Criar diretório se não existir
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Gerar nome único para o arquivo
        String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
        Path filePath = uploadPath.resolve(nomeArquivo);

        // Salvar arquivo
        Files.copy(arquivo.getInputStream(), filePath);

        // Criar entidade Anexo
        Anexo anexo = anexoMapper.toEntityFromMultipartFile(arquivo, mensagem, filePath.toString());
        Anexo anexoSalvo = anexoRepository.save(anexo);

        return anexoMapper.toDTO(anexoSalvo);
    }

    @Transactional
    public void excluir(String id) throws IOException {
        Anexo anexo = anexoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado com ID: " + id));

        // Excluir arquivo do sistema de arquivos
        Path filePath = Paths.get(anexo.getCaminho());
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        anexoRepository.delete(anexo);
    }

    public byte[] downloadArquivo(String id) throws IOException {
        Anexo anexo = anexoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado com ID: " + id));

        Path filePath = Paths.get(anexo.getCaminho());
        if (!Files.exists(filePath)) {
            throw new RuntimeException("Arquivo não encontrado no servidor");
        }

        return Files.readAllBytes(filePath);
    }

    @Transactional
    public void excluirPorMensagem(String mensagemId) throws IOException {
        List<Anexo> anexos = anexoRepository.findByMensagemId(mensagemId);

        for (Anexo anexo : anexos) {
            // Excluir arquivo do sistema de arquivos
            Path filePath = Paths.get(anexo.getCaminho());
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        }

        anexoRepository.deleteByMensagemId(mensagemId);
    }
}