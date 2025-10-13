package com.helpflow.application.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String departamentoId;
    private String departamentoNome;
    private String perfilId;
    private String perfilNome;
    private String avatar;
    private Boolean ativo;
    private LocalDateTime createdAt;
}