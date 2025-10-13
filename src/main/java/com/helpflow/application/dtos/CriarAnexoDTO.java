package com.helpflow.application.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CriarAnexoDTO {
    private String nomeArquivo;
    private String caminho;
    private String tipo;
    private Long tamanho;
    private MultipartFile arquivo; // Para upload de arquivos
}