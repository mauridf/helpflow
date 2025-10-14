package com.helpflow.application.dtos;

import lombok.Data;

@Data
public class EstatisticasClienteDTO {
    private Long totalTicketsAbertos;
    private Long totalTicketsResolvidos;
    private Long totalTicketsAtrasados;
    private Double satisfacaoMedia;
}
