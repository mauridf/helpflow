package com.helpflow.domain.entities;

import com.helpflow.domain.enums.StatusTicket;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;

    private String codigo; // TKT-2024-001
    private String titulo;
    private String descricao;

    @DBRef
    private Usuario cliente;

    @DBRef
    private Usuario atendente;

    @DBRef
    private Categoria categoria;

    @DBRef
    private Prioridade prioridade;

    @DBRef
    private Departamento departamento;

    private StatusTicket status;

    @DBRef
    private SLA sla;

    private Integer tempoEstimado; // minutos
    private Integer tempoGasto; // minutos
    private LocalDateTime dataLimite;
    private LocalDateTime dataFechamento;

    private Integer avaliacao; // 1-5
    private String feedback;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Ticket() {
        this.status = StatusTicket.ABERTO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.tempoGasto = 0;
    }

    // Métodos de domínio
    public void atribuirAtendente(Usuario atendente) {
        if (!atendente.isAtendente() && !atendente.isGestor()) {
            throw new IllegalArgumentException("Apenas atendentes ou gestores podem ser atribuídos a tickets");
        }
        this.atendente = atendente;
        this.status = StatusTicket.EM_ANDAMENTO;
        this.updatedAt = LocalDateTime.now();
    }

    public void alterarStatus(StatusTicket novoStatus) {
        this.status = novoStatus;
        this.updatedAt = LocalDateTime.now();

        if (novoStatus == StatusTicket.FECHADO || novoStatus == StatusTicket.RESOLVIDO) {
            this.dataFechamento = LocalDateTime.now();
        }
    }

    public void adicionarTempoGasto(Integer minutos) {
        this.tempoGasto += minutos;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isAtrasado() {
        if (dataLimite == null) return false;
        return LocalDateTime.now().isAfter(dataLimite);
    }

    public boolean isDentroSLA() {
        if (sla == null || dataLimite == null) return true;
        return !isAtrasado();
    }

    public void avaliar(Integer nota, String comentario) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Avaliação deve ser entre 1 e 5");
        }
        this.avaliacao = nota;
        this.feedback = comentario;
        this.updatedAt = LocalDateTime.now();
    }

    // Gera código único para o ticket
    public void gerarCodigo(String sequencial) {
        int ano = LocalDateTime.now().getYear();
        this.codigo = String.format("TKT-%d-%s", ano, sequencial);
    }
}