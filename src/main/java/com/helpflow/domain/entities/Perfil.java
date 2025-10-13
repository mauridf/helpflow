package com.helpflow.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "perfis")
public class Perfil {
    @Id
    private String id;

    private String nome;
    private Integer nivelAcesso;
    private String permissoes; // JSON como string por simplicidade inicial

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor para os perfis predefinidos
    public Perfil(String nome, Integer nivelAcesso) {
        this.nome = nome;
        this.nivelAcesso = nivelAcesso;
        this.permissoes = "{}";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Métodos de domínio
    public boolean isCliente() {
        return "Cliente".equalsIgnoreCase(this.nome);
    }

    public boolean isAtendente() {
        return "Atendente".equalsIgnoreCase(this.nome);
    }

    public boolean isGestor() {
        return "Gestor".equalsIgnoreCase(this.nome);
    }
}