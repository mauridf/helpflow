package com.helpflow.application.mappers;

import com.helpflow.application.dtos.TicketDTO;
import com.helpflow.application.dtos.CriarTicketDTO;
import com.helpflow.application.dtos.AtualizarTicketDTO;
import com.helpflow.domain.entities.*;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketDTO toDTO(Ticket ticket) {
        if (ticket == null) return null;

        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setCodigo(ticket.getCodigo());
        dto.setTitulo(ticket.getTitulo());
        dto.setDescricao(ticket.getDescricao());
        dto.setStatus(ticket.getStatus());
        dto.setTempoEstimado(ticket.getTempoEstimado());
        dto.setTempoGasto(ticket.getTempoGasto());
        dto.setDataLimite(ticket.getDataLimite());
        dto.setDataFechamento(ticket.getDataFechamento());
        dto.setAvaliacao(ticket.getAvaliacao());
        dto.setFeedback(ticket.getFeedback());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setAtrasado(ticket.isAtrasado());

        // Dados relacionados
        if (ticket.getCliente() != null) {
            dto.setClienteId(ticket.getCliente().getId());
            dto.setClienteNome(ticket.getCliente().getNome());
        }

        if (ticket.getAtendente() != null) {
            dto.setAtendenteId(ticket.getAtendente().getId());
            dto.setAtendenteNome(ticket.getAtendente().getNome());
        }

        if (ticket.getCategoria() != null) {
            dto.setCategoriaId(ticket.getCategoria().getId());
            dto.setCategoriaNome(ticket.getCategoria().getNome());
        }

        if (ticket.getPrioridade() != null) {
            dto.setPrioridadeId(ticket.getPrioridade().getId());
            dto.setPrioridadeNome(ticket.getPrioridade().getNome());
        }

        if (ticket.getDepartamento() != null) {
            dto.setDepartamentoId(ticket.getDepartamento().getId());
            dto.setDepartamentoNome(ticket.getDepartamento().getNome());
        }

        if (ticket.getSla() != null) {
            dto.setSlaId(ticket.getSla().getId());
            dto.setSlaNome(ticket.getSla().getNome());
        }

        return dto;
    }

    public Ticket toEntity(CriarTicketDTO dto, Usuario cliente, Categoria categoria,
                           Prioridade prioridade, Departamento departamento, SLA sla) {
        Ticket ticket = new Ticket();
        ticket.setTitulo(dto.getTitulo());
        ticket.setDescricao(dto.getDescricao());
        ticket.setCliente(cliente);
        ticket.setCategoria(categoria);
        ticket.setPrioridade(prioridade);
        ticket.setDepartamento(departamento);
        ticket.setSla(sla);

        // Gerar código temporário - será atualizado no service
        ticket.gerarCodigo("0001");

        return ticket;
    }

    public void updateEntity(Ticket ticket, AtualizarTicketDTO dto, Categoria categoria,
                             Prioridade prioridade, Usuario atendente) {
        if (dto.getTitulo() != null) ticket.setTitulo(dto.getTitulo());
        if (dto.getDescricao() != null) ticket.setDescricao(dto.getDescricao());
        if (dto.getStatus() != null) ticket.alterarStatus(dto.getStatus());
        if (dto.getTempoEstimado() != null) ticket.setTempoEstimado(dto.getTempoEstimado());
        if (categoria != null) ticket.setCategoria(categoria);
        if (prioridade != null) ticket.setPrioridade(prioridade);
        if (atendente != null) ticket.atribuirAtendente(atendente);
    }
}