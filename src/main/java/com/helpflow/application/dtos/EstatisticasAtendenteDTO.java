package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class EstatisticasAtendenteDTO {
    private Long ticketsResolvidosPeriodo;
    private Double tempoMedioResposta;
    private Double conformidadeSLA;
    private Double avaliacaoMedia;
    private Long ticketsEmAndamento;
}
