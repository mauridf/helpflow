package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class CriarTicketDTO {
    private String titulo;
    private String descricao;
    private String categoriaId;
    private String prioridadeId;
    private String departamentoId;
}