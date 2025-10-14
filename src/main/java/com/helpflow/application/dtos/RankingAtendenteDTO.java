package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class RankingAtendenteDTO {
    private String usuarioId;
    private String usuarioNome;
    private Long ticketsResolvidos;
    private Double avaliacaoMedia;
    private Double tempoMedioResposta;
}
