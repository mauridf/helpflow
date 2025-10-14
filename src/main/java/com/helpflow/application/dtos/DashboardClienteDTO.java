package com.helpflow.application.dtos;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DashboardClienteDTO {
    private EstatisticasClienteDTO estatisticas;
    private List<TicketDTO> ticketsRecentes;
    private List<NotificacaoDTO> notificacoes;
}