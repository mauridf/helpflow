package com.helpflow.application.mappers;

import com.helpflow.application.dtos.AnexoDTO;
import com.helpflow.application.dtos.CriarAnexoDTO;
import com.helpflow.domain.entities.Anexo;
import com.helpflow.domain.entities.Mensagem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class AnexoMapper {

    public AnexoDTO toDTO(Anexo anexo) {
        if (anexo == null) return null;

        AnexoDTO dto = new AnexoDTO();
        dto.setId(anexo.getId());
        dto.setNomeArquivo(anexo.getNomeArquivo());
        dto.setCaminho(anexo.getCaminho());
        dto.setTipo(anexo.getTipo());
        dto.setTamanho(anexo.getTamanho());
        dto.setTamanhoFormatado(anexo.getTamanhoFormatado());
        dto.setIsImagem(anexo.isImagem());
        dto.setIsDocumento(anexo.isDocumento());
        dto.setCreatedAt(anexo.getCreatedAt());

        if (anexo.getMensagem() != null) {
            dto.setMensagemId(anexo.getMensagem().getId());
        }

        return dto;
    }

    public Anexo toEntity(CriarAnexoDTO dto, Mensagem mensagem) {
        Anexo anexo = new Anexo();
        anexo.setMensagem(mensagem);
        anexo.setNomeArquivo(dto.getNomeArquivo());
        anexo.setCaminho(dto.getCaminho());
        anexo.setTipo(dto.getTipo());
        anexo.setTamanho(dto.getTamanho());
        return anexo;
    }

    public Anexo toEntityFromMultipartFile(MultipartFile arquivo, Mensagem mensagem, String caminho) throws IOException {
        Anexo anexo = new Anexo();
        anexo.setMensagem(mensagem);
        anexo.setNomeArquivo(arquivo.getOriginalFilename());
        anexo.setCaminho(caminho);
        anexo.setTipo(arquivo.getContentType());
        anexo.setTamanho(arquivo.getSize());
        return anexo;
    }
}