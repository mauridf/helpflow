package com.helpflow.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Document(collection = "departamentos")
public class Departamento {
    @Id
    private String id;

    private String nome;
    private String descricao;
    private String email;

    @DBRef
    private Usuario responsavel;

    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Departamento() {
        this.ativo = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Métodos de domínio
    public void desativar() {
        this.ativo = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void ativar() {
        this.ativo = true;
        this.updatedAt = LocalDateTime.now();
    }
}