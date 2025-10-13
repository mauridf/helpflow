package com.helpflow.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Document(collection = "historico_tickets")
public class HistoricoTicket {
    @Id
    private String id;

    @DBRef
    private Ticket ticket;

    @DBRef
    private Usuario usuario;

    private String acao;
    private String descricao;
    private String dadosAnteriores; // JSON como string
    private String dadosNovos; // JSON como string
    private LocalDateTime createdAt;

    public HistoricoTicket() {
        this.createdAt = LocalDateTime.now();
    }

    // Factory method para criar histórico
    public static HistoricoTicket criar(Ticket ticket, Usuario usuario, String acao,
                                        String descricao, String dadosAnteriores, String dadosNovos) {
        HistoricoTicket historico = new HistoricoTicket();
        historico.ticket = ticket;
        historico.usuario = usuario;
        historico.acao = acao;
        historico.descricao = descricao;
        historico.dadosAnteriores = dadosAnteriores;
        historico.dadosNovos = dadosNovos;
        return historico;
    }
}