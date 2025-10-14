package com.helpflow.application.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class MetricasSLADTO {
    private Double percentualConformidade;
    private Long ticketsForaSLA;
    private Double tempoMedioResolucao;
    private Map<String, Long> conformidadePorCategoria;
}
