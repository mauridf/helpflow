package com.helpflow.application.dtos;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AnalisesTemporaisDTO {
    private Map<String, Long> ticketsPorPeriodo;
    private List<String> picosDemanda;
    private Map<String, Double> tendenciasSazonais;
}
