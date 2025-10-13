package com.helpflow.application.services;

import com.helpflow.application.dtos.MensagemDTO;
import com.helpflow.application.dtos.CriarMensagemDTO;
import com.helpflow.application.dtos.AtualizarMensagemDTO;
import com.helpflow.application.mappers.MensagemMapper;
import com.helpflow.domain.entities.Mensagem;
import com.helpflow.domain.entities.Ticket;
import com.helpflow.domain.entities.Usuario;
import com.helpflow.domain.entities.Anexo;
import com.helpflow.infrastructure.persistence.mongodb.MensagemRepository;
import com.helpflow.infrastructure.persistence.mongodb.TicketRepository;
import com.helpflow.infrastructure.persistence.mongodb.UsuarioRepository;
import com.helpflow.infrastructure.persistence.mongodb.AnexoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final AnexoRepository anexoRepository;
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final MensagemMapper mensagemMapper;

    public List<MensagemDTO> listarPorTicket(String ticketId) {
        List<Mensagem> mensagens = mensagemRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
        return mensagens.stream()
                .map(mensagem -> {
                    List<Anexo> anexos = anexoRepository.findByMensagemId(mensagem.getId());
                    return mensagemMapper.toDTOWithAnexos(mensagem, anexos);
                })
                .collect(Collectors.toList());
    }

    public List<MensagemDTO> listarPublicasPorTicket(String ticketId) {
        List<Mensagem> mensagens = mensagemRepository.findByTicketIdAndInternaFalse(ticketId);
        return mensagens.stream()
                .map(mensagemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MensagemDTO buscarPorId(String id) {
        Mensagem mensagem = mensagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com ID: " + id));

        List<Anexo> anexos = anexoRepository.findByMensagemId(id);
        return mensagemMapper.toDTOWithAnexos(mensagem, anexos);
    }

    public Long contarMensagensNaoLidas(String ticketId, String usuarioId) {
        return mensagemRepository.countByTicketIdAndLidaFalseAndUsuarioIdNot(ticketId, usuarioId);
    }

    @Transactional
    public MensagemDTO criar(CriarMensagemDTO dto, String ticketId, String usuarioId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Mensagem mensagem = mensagemMapper.toEntity(dto, ticket, usuario);
        Mensagem mensagemSalva = mensagemRepository.save(mensagem);

        // Atualizar timestamp do ticket
        ticket.setUpdatedAt(java.time.LocalDateTime.now());
        ticketRepository.save(ticket);

        return mensagemMapper.toDTO(mensagemSalva);
    }

    @Transactional
    public MensagemDTO atualizar(String id, AtualizarMensagemDTO dto) {
        Mensagem mensagem = mensagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com ID: " + id));

        mensagemMapper.updateEntity(mensagem, dto);
        Mensagem mensagemAtualizada = mensagemRepository.save(mensagem);

        List<Anexo> anexos = anexoRepository.findByMensagemId(id);
        return mensagemMapper.toDTOWithAnexos(mensagemAtualizada, anexos);
    }

    @Transactional
    public void marcarComoLida(String id) {
        Mensagem mensagem = mensagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com ID: " + id));

        mensagem.marcarComoLida();
        mensagemRepository.save(mensagem);
    }

    @Transactional
    public void marcarTodasComoLidas(String ticketId, String usuarioId) {
        List<Mensagem> mensagens = mensagemRepository.findByTicketIdAndLidaFalseAndUsuarioIdNot(ticketId, usuarioId);
        mensagens.forEach(Mensagem::marcarComoLida);
        mensagemRepository.saveAll(mensagens);
    }

    @Transactional
    public void excluir(String id) {
        if (!mensagemRepository.existsById(id)) {
            throw new RuntimeException("Mensagem não encontrada com ID: " + id);
        }

        // Excluir anexos primeiro
        anexoRepository.deleteByMensagemId(id);
        mensagemRepository.deleteById(id);
    }
}