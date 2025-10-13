package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class CriarCategoriaDTO {
    private String nome;
    private String departamentoId;
    private String slaId;
    private String cor;
}