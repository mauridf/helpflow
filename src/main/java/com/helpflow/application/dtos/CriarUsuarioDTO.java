package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class CriarUsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String departamentoId;
    private String perfilId;
}