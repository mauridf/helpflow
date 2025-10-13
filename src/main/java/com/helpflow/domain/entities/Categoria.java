package com.helpflow.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Document(collection = "categorias")
public class Categoria {
    @Id
    private String id;

    private String nome;

    @DBRef
    private Departamento departamento;

    @DBRef
    private SLA sla;

    private String cor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Categoria() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}