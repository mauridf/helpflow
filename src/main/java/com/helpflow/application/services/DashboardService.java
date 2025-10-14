package com.helpflow.application.services;

import com.helpflow.application.dtos.*;
import com.helpflow.domain.entities.Ticket;
import com.helpflow.domain.entities.Usuario;
import com.helpflow.domain.enums.StatusTicket;
import com.helpflow.infrastructure.persistence.mongodb.TicketRepository;
import com.helpflow.infrastructure.persistence.mongodb.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    // Dashboard do Cliente
    public DashboardClienteDTO getDashboardCliente(String clienteId) {
        DashboardClienteDTO dashboard = new DashboardClienteDTO();

        // Estatísticas
        EstatisticasClienteDTO estatisticas = new EstatisticasClienteDTO();
        List<Ticket> ticketsCliente = ticketRepository.findByClienteId(clienteId);

        estatisticas.setTotalTicketsAbertos(
                ticketsCliente.stream()
                        .filter(t -> t.getStatus() == StatusTicket.ABERTO || t.getStatus() == StatusTicket.EM_ANDAMENTO)
                        .count()
        );

        estatisticas.setTotalTicketsResolvidos(
                ticketsCliente.stream()
                        .filter(t -> t.getStatus() == StatusTicket.RESOLVIDO || t.getStatus() == StatusTicket.FECHADO)
                        .count()
        );

        estatisticas.setTotalTicketsAtrasados(
                ticketsCliente.stream()
                        .filter(Ticket::isAtrasado)
                        .count()
        );

        estatisticas.setSatisfacaoMedia(
                ticketsCliente.stream()
                        .filter(t -> t.getAvaliacao() != null)
                        .mapToInt(Ticket::getAvaliacao)
                        .average()
                        .orElse(0.0)
        );

        dashboard.setEstatisticas(estatisticas);

        // Tickets recentes (últimos 5)
        List<Ticket> ticketsRecentes = ticketsCliente.stream()
                .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
                .limit(5)
                .collect(Collectors.toList());

        // Notificações simuladas
        List<NotificacaoDTO> notificacoes = Arrays.asList(
                criarNotificacao("Novo ticket respondido", "Seu ticket TKT-2024-0001 foi respondido", "info"),
                criarNotificacao("Ticket resolvido", "Seu ticket TKT-2024-0002 foi marcado como resolvido", "success"),
                criarNotificacao("Avaliação solicitada", "Por favor, avalie o atendimento do ticket TKT-2024-0003", "warning")
        );

        dashboard.setNotificacoes(notificacoes);

        return dashboard;
    }

    // Dashboard do Atendente
    public DashboardAtendenteDTO getDashboardAtendente(String atendenteId) {
        DashboardAtendenteDTO dashboard = new DashboardAtendenteDTO();

        // Estatísticas
        EstatisticasAtendenteDTO estatisticas = new EstatisticasAtendenteDTO();
        List<Ticket> ticketsAtendente = ticketRepository.findByAtendenteId(atendenteId);
        LocalDateTime umMesAtras = LocalDateTime.now().minusMonths(1);

        estatisticas.setTicketsResolvidosPeriodo(
                ticketsAtendente.stream()
                        .filter(t -> t.getDataFechamento() != null && t.getDataFechamento().isAfter(umMesAtras))
                        .count()
        );

        estatisticas.setTicketsEmAndamento(
                ticketsAtendente.stream()
                        .filter(t -> t.getStatus() == StatusTicket.EM_ANDAMENTO)
                        .count()
        );

        // Cálculos simplificados para exemplo
        estatisticas.setTempoMedioResposta(45.5);
        estatisticas.setConformidadeSLA(85.2);
        estatisticas.setAvaliacaoMedia(4.3);

        dashboard.setEstatisticas(estatisticas);

        // Tickets atribuídos
        List<Ticket> ticketsAtribuidos = ticketsAtendente.stream()
                .filter(t -> t.getStatus() == StatusTicket.EM_ANDAMENTO || t.getStatus() == StatusTicket.ABERTO)
                .sorted((t1, t2) -> {
                    // Ordenar por prioridade (maior nível primeiro) e depois por data limite
                    int prioridadeCompare = Integer.compare(t2.getPrioridade().getNivel(), t1.getPrioridade().getNivel());
                    if (prioridadeCompare != 0) return prioridadeCompare;
                    return t1.getDataLimite().compareTo(t2.getDataLimite());
                })
                .limit(10)
                .collect(Collectors.toList());

        // Alertas
        List<AlertaDTO> alertas = criarAlertasAtendente(ticketsAtendente);
        dashboard.setAlertas(alertas);

        // Atividades recentes
        List<AtividadeRecenteDTO> atividades = Arrays.asList(
                criarAtividade("Ticket respondido", "Resposta enviada para TKT-2024-0001"),
                criarAtividade("Ticket resolvido", "TKT-2024-0002 marcado como resolvido"),
                criarAtividade("Novo ticket atribuído", "TKT-2024-0005 atribuído para você")
        );
        dashboard.setAtividadesRecentes(atividades);

        return dashboard;
    }

    // Dashboard do Gestor
    public DashboardGestorDTO getDashboardGestor() {
        DashboardGestorDTO dashboard = new DashboardGestorDTO();

        // Visão Geral
        VisaoGeralDTO visaoGeral = new VisaoGeralDTO();
        List<Ticket> todosTickets = ticketRepository.findAll();

        visaoGeral.setTotalTickets((long) todosTickets.size());
        visaoGeral.setTicketsAbertos(
                todosTickets.stream()
                        .filter(t -> t.getStatus() == StatusTicket.ABERTO || t.getStatus() == StatusTicket.EM_ANDAMENTO)
                        .count()
        );
        visaoGeral.setTicketsFechados(
                todosTickets.stream()
                        .filter(t -> t.getStatus() == StatusTicket.FECHADO || t.getStatus() == StatusTicket.RESOLVIDO)
                        .count()
        );

        // Distribuição por status
        Map<String, Long> distribuicaoStatus = todosTickets.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getStatus().name(),
                        Collectors.counting()
                ));
        visaoGeral.setDistribuicaoStatus(distribuicaoStatus);

        dashboard.setVisaoGeral(visaoGeral);

        // Métricas SLA
        MetricasSLADTO metricasSLA = new MetricasSLADTO();
        long ticketsDentroSLA = todosTickets.stream().filter(t -> !t.isAtrasado()).count();
        metricasSLA.setPercentualConformidade(
                todosTickets.isEmpty() ? 0.0 : (double) ticketsDentroSLA / todosTickets.size() * 100
        );
        metricasSLA.setTicketsForaSLA(todosTickets.stream().filter(Ticket::isAtrasado).count());

        // Tempo médio simplificado
        metricasSLA.setTempoMedioResolucao(18.5);
        dashboard.setMetricasSLA(metricasSLA);

        // Desempenho da Equipa
        DesempenhoEquipaDTO desempenhoEquipa = new DesempenhoEquipaDTO();
        List<Usuario> atendentes = usuarioRepository.findByPerfilNome("Atendente");

        List<RankingAtendenteDTO> ranking = atendentes.stream()
                .map(this::criarRankingAtendente)
                .sorted((a1, a2) -> Double.compare(a2.getAvaliacaoMedia(), a1.getAvaliacaoMedia()))
                .collect(Collectors.toList());

        desempenhoEquipa.setRankingAtendentes(ranking);
        dashboard.setDesempenhoEquipa(desempenhoEquipa);

        // Análises Temporais
        AnalisesTemporaisDTO analisesTemporais = new AnalisesTemporaisDTO();

        // Tickets por período (últimos 7 dias)
        Map<String, Long> ticketsPorDia = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime dia = LocalDateTime.now().minusDays(i);
            String diaFormatado = dia.format(DateTimeFormatter.ofPattern("dd/MM"));
            long count = todosTickets.stream()
                    .filter(t -> t.getCreatedAt().toLocalDate().equals(dia.toLocalDate()))
                    .count();
            ticketsPorDia.put(diaFormatado, count);
        }
        analisesTemporais.setTicketsPorPeriodo(ticketsPorDia);

        dashboard.setAnalisesTemporais(analisesTemporais);

        return dashboard;
    }

    // Métodos auxiliares
    private NotificacaoDTO criarNotificacao(String titulo, String mensagem, String tipo) {
        NotificacaoDTO notificacao = new NotificacaoDTO();
        notificacao.setId(UUID.randomUUID().toString());
        notificacao.setTitulo(titulo);
        notificacao.setMensagem(mensagem);
        notificacao.setTipo(tipo);
        notificacao.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
        notificacao.setLida(false);
        return notificacao;
    }

    private List<AlertaDTO> criarAlertasAtendente(List<Ticket> tickets) {
        return tickets.stream()
                .filter(Ticket::isAtrasado)
                .filter(t -> t.getStatus() != StatusTicket.FECHADO && t.getStatus() != StatusTicket.RESOLVIDO)
                .map(ticket -> {
                    AlertaDTO alerta = new AlertaDTO();
                    alerta.setId(UUID.randomUUID().toString());
                    alerta.setTitulo("Ticket Atrasado");
                    alerta.setMensagem("O ticket " + ticket.getCodigo() + " está fora do prazo");
                    alerta.setPrioridade("alta");
                    alerta.setTicketId(ticket.getId());
                    return alerta;
                })
                .limit(5)
                .collect(Collectors.toList());
    }

    private AtividadeRecenteDTO criarAtividade(String acao, String descricao) {
        AtividadeRecenteDTO atividade = new AtividadeRecenteDTO();
        atividade.setId(UUID.randomUUID().toString());
        atividade.setAcao(acao);
        atividade.setDescricao(descricao);
        atividade.setUsuario("Sistema");
        atividade.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
        return atividade;
    }

    private RankingAtendenteDTO criarRankingAtendente(Usuario atendente) {
        RankingAtendenteDTO ranking = new RankingAtendenteDTO();
        ranking.setUsuarioId(atendente.getId());
        ranking.setUsuarioNome(atendente.getNome());

        List<Ticket> ticketsAtendente = ticketRepository.findByAtendenteId(atendente.getId());

        ranking.setTicketsResolvidos(
                ticketsAtendente.stream()
                        .filter(t -> t.getStatus() == StatusTicket.RESOLVIDO || t.getStatus() == StatusTicket.FECHADO)
                        .count()
        );

        ranking.setAvaliacaoMedia(
                ticketsAtendente.stream()
                        .filter(t -> t.getAvaliacao() != null)
                        .mapToInt(Ticket::getAvaliacao)
                        .average()
                        .orElse(0.0)
        );

        ranking.setTempoMedioResposta(32.1); // Valor simplificado

        return ranking;
    }
}