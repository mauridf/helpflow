package com.helpflow.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Document(collection = "anexos")
public class Anexo {
    @Id
    private String id;

    @DBRef
    private Mensagem mensagem;

    private String nomeArquivo;
    private String caminho;
    private String tipo;
    private Long tamanho; // bytes
    private LocalDateTime createdAt;

    public Anexo() {
        this.createdAt = LocalDateTime.now();
    }

    // Métodos de domínio
    public String getTamanhoFormatado() {
        if (tamanho < 1024) return tamanho + " B";
        else if (tamanho < 1048576) return String.format("%.1f KB", tamanho / 1024.0);
        else return String.format("%.1f MB", tamanho / 1048576.0);
    }

    public boolean isImagem() {
        return tipo != null && tipo.startsWith("image/");
    }

    public boolean isDocumento() {
        return tipo != null && (
                tipo.contains("pdf") ||
                        tipo.contains("word") ||
                        tipo.contains("excel") ||
                        tipo.contains("text")
        );
    }
}