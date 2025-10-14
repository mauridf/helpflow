package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class TokenDTO {
    private String token;
    private String tipo;
    private String usuarioId;
    private String usuarioNome;
    private String perfil;

    public TokenDTO(String token, String tipo, String usuarioId, String usuarioNome, String perfil) {
        this.token = token;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.perfil = perfil;
    }
}