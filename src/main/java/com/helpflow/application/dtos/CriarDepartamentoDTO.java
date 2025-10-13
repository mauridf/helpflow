package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class CriarDepartamentoDTO {
    private String nome;
    private String descricao;
    private String email;
    private String responsavelId;
}