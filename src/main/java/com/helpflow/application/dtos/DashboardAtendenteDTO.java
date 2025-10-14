package com.helpflow.application.dtos;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DashboardAtendenteDTO {
    private EstatisticasAtendenteDTO estatisticas;
    private List<TicketDTO> ticketsAtribuidos;
    private List<AlertaDTO> alertas;
    private List<AtividadeRecenteDTO> atividadesRecentes;
}