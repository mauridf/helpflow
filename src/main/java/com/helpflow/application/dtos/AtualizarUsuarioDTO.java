package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class AtualizarUsuarioDTO {
    private String nome;
    private String telefone;
    private String departamentoId;
    private String avatar;
}