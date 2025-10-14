package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class AtividadeRecenteDTO {
    private String id;
    private String acao;
    private String descricao;
    private String usuario;
    private String data;
}
