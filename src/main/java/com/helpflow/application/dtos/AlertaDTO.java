package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class AlertaDTO {
    private String id;
    private String titulo;
    private String mensagem;
    private String prioridade;
    private String ticketId;
}
