package com.helpflow.application.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class VisaoGeralDTO {
    private Long totalTickets;
    private Long ticketsAbertos;
    private Long ticketsFechados;
    private Map<String, Long> distribuicaoStatus;
    private Map<String, Long> volumePorDepartamento;
}
