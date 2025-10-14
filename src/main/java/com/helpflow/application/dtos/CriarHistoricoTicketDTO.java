package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class CriarHistoricoTicketDTO {
    private String ticketId;
    private String usuarioId;
    private String acao;
    private String descricao;
    private String dadosAnteriores;
    private String dadosNovos;
}