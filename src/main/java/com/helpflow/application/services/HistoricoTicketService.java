package com.helpflow.application.services;

import com.helpflow.application.dtos.HistoricoTicketDTO;
import com.helpflow.application.dtos.CriarHistoricoTicketDTO;
import com.helpflow.application.mappers.HistoricoTicketMapper;
import com.helpflow.domain.entities.HistoricoTicket;
import com.helpflow.domain.entities.Ticket;
import com.helpflow.domain.entities.Usuario;
import com.helpflow.infrastructure.persistence.mongodb.HistoricoTicketRepository;
import com.helpflow.infrastructure.persistence.mongodb.TicketRepository;
import com.helpflow.infrastructure.persistence.mongodb.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoricoTicketService {

    private final HistoricoTicketRepository historicoTicketRepository;
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistoricoTicketMapper historicoTicketMapper;

    public List<HistoricoTicketDTO> listarPorTicket(String ticketId) {
        return historicoTicketRepository.findByTicketIdOrderByCreatedAtDesc(ticketId).stream()
                .map(historicoTicketMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HistoricoTicketDTO> listarPorUsuario(String usuarioId) {
        return historicoTicketRepository.findByUsuarioId(usuarioId).stream()
                .map(historicoTicketMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HistoricoTicketDTO> listarPorAcao(String acao) {
        return historicoTicketRepository.findByAcao(acao).stream()
                .map(historicoTicketMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public HistoricoTicketDTO criar(CriarHistoricoTicketDTO dto) {
        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        HistoricoTicket historico = historicoTicketMapper.toEntity(dto, ticket, usuario);
        HistoricoTicket historicoSalvo = historicoTicketRepository.save(historico);

        return historicoTicketMapper.toDTO(historicoSalvo);
    }

    @Transactional
    public void registrarAcao(String ticketId, String usuarioId, String acao, String descricao,
                              String dadosAnteriores, String dadosNovos) {
        CriarHistoricoTicketDTO dto = new CriarHistoricoTicketDTO();
        dto.setTicketId(ticketId);
        dto.setUsuarioId(usuarioId);
        dto.setAcao(acao);
        dto.setDescricao(descricao);
        dto.setDadosAnteriores(dadosAnteriores);
        dto.setDadosNovos(dadosNovos);

        criar(dto);
    }
}