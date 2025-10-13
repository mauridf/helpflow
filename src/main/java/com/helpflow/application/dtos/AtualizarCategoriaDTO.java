package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class AtualizarCategoriaDTO {
    private String nome;
    private String departamentoId;
    private String slaId;
    private String cor;
}