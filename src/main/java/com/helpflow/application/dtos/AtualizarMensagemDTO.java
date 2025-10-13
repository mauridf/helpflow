package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class AtualizarMensagemDTO {
    private String mensagem;
    private Boolean lida;
}