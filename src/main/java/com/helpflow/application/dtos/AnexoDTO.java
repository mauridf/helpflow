package com.helpflow.application.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnexoDTO {
    private String id;
    private String mensagemId;
    private String nomeArquivo;
    private String caminho;
    private String tipo;
    private Long tamanho;
    private String tamanhoFormatado;
    private Boolean isImagem;
    private Boolean isDocumento;
    private LocalDateTime createdAt;
}