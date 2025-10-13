package com.helpflow.application.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DepartamentoDTO {
    private String id;
    private String nome;
    private String descricao;
    private String email;
    private String responsavelId;
    private String responsavelNome;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}