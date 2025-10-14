package com.helpflow.application.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistoricoTicketDTO {
    private String id;
    private String ticketId;
    private String ticketCodigo;
    private String usuarioId;
    private String usuarioNome;
    private String acao;
    private String descricao;
    private String dadosAnteriores;
    private String dadosNovos;
    private LocalDateTime createdAt;
}