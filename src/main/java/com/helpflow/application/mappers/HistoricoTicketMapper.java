package com.helpflow.application.mappers;

import com.helpflow.application.dtos.HistoricoTicketDTO;
import com.helpflow.application.dtos.CriarHistoricoTicketDTO;
import com.helpflow.domain.entities.HistoricoTicket;
import com.helpflow.domain.entities.Ticket;
import com.helpflow.domain.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class HistoricoTicketMapper {

    public HistoricoTicketDTO toDTO(HistoricoTicket historico) {
        if (historico == null) return null;

        HistoricoTicketDTO dto = new HistoricoTicketDTO();
        dto.setId(historico.getId());
        dto.setAcao(historico.getAcao());
        dto.setDescricao(historico.getDescricao());
        dto.setDadosAnteriores(historico.getDadosAnteriores());
        dto.setDadosNovos(historico.getDadosNovos());
        dto.setCreatedAt(historico.getCreatedAt());

        if (historico.getTicket() != null) {
            dto.setTicketId(historico.getTicket().getId());
            dto.setTicketCodigo(historico.getTicket().getCodigo());
        }

        if (historico.getUsuario() != null) {
            dto.setUsuarioId(historico.getUsuario().getId());
            dto.setUsuarioNome(historico.getUsuario().getNome());
        }

        return dto;
    }

    public HistoricoTicket toEntity(CriarHistoricoTicketDTO dto, Ticket ticket, Usuario usuario) {
        HistoricoTicket historico = new HistoricoTicket();
        historico.setTicket(ticket);
        historico.setUsuario(usuario);
        historico.setAcao(dto.getAcao());
        historico.setDescricao(dto.getDescricao());
        historico.setDadosAnteriores(dto.getDadosAnteriores());
        historico.setDadosNovos(dto.getDadosNovos());
        return historico;
    }
}