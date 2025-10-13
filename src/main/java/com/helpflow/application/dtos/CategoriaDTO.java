package com.helpflow.application.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoriaDTO {
    private String id;
    private String nome;
    private String departamentoId;
    private String departamentoNome;
    private String slaId;
    private String slaNome;
    private String cor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}