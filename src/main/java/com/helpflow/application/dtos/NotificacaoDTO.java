package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class NotificacaoDTO {
    private String id;
    private String titulo;
    private String mensagem;
    private String tipo;
    private String data;
    private Boolean lida;
}