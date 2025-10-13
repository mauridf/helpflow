package com.helpflow.application.dtos;

import com.helpflow.domain.enums.StatusTicket;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TicketDTO {
    private String id;
    private String codigo;
    private String titulo;
    private String descricao;
    private String clienteId;
    private String clienteNome;
    private String atendenteId;
    private String atendenteNome;
    private String categoriaId;
    private String categoriaNome;
    private String prioridadeId;
    private String prioridadeNome;
    private String departamentoId;
    private String departamentoNome;
    private StatusTicket status;
    private String slaId;
    private String slaNome;
    private Integer tempoEstimado;
    private Integer tempoGasto;
    private LocalDateTime dataLimite;
    private LocalDateTime dataFechamento;
    private Integer avaliacao;
    private String feedback;
    private LocalDateTime createdAt;
    private Boolean atrasado;
}