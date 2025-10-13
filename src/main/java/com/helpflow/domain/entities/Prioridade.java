package com.helpflow.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "prioridades")
public class Prioridade {
    @Id
    private String id;

    private String nome;
    private Integer nivel;
    private String cor;
    private String icone;

    // Construtor para prioridades predefinidas
    public Prioridade(String nome, Integer nivel, String cor, String icone) {
        this.nome = nome;
        this.nivel = nivel;
        this.cor = cor;
        this.icone = icone;
    }
}