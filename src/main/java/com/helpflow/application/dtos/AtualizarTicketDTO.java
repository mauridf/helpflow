package com.helpflow.application.dtos;

import com.helpflow.domain.enums.StatusTicket;
import lombok.Data;

@Data
public class AtualizarTicketDTO {
    private String titulo;
    private String descricao;
    private String categoriaId;
    private String prioridadeId;
    private String atendenteId;
    private StatusTicket status;
    private Integer tempoEstimado;
}