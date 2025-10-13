package com.helpflow.application.services;

import com.helpflow.application.dtos.TicketDTO;
import com.helpflow.application.dtos.CriarTicketDTO;
import com.helpflow.application.dtos.AtualizarTicketDTO;
import com.helpflow.application.mappers.TicketMapper;
import com.helpflow.domain.entities.*;
import com.helpflow.domain.enums.StatusTicket;
import com.helpflow.infrastructure.persistence.mongodb.TicketRepository;
import com.helpflow.infrastructure.persistence.mongodb.UsuarioRepository;
import com.helpflow.infrastructure.persistence.mongodb.CategoriaRepository;
import com.helpflow.infrastructure.persistence.mongodb.PrioridadeRepository;
import com.helpflow.infrastructure.persistence.mongodb.DepartamentoRepository;
import com.helpflow.infrastructure.persistence.mongodb.SLARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final PrioridadeRepository prioridadeRepository;
    private final DepartamentoRepository departamentoRepository;
    private final SLARepository slaRepository;
    private final TicketMapper ticketMapper;

    public List<TicketDTO> listarTodos() {
        return ticketRepository.findAll().stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TicketDTO buscarPorId(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado com ID: " + id));
        return ticketMapper.toDTO(ticket);
    }

    public TicketDTO buscarPorCodigo(String codigo) {
        Ticket ticket = ticketRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado com código: " + codigo));
        return ticketMapper.toDTO(ticket);
    }

    public List<TicketDTO> listarPorCliente(String clienteId) {
        return ticketRepository.findByClienteId(clienteId).stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketDTO> listarPorAtendente(String atendenteId) {
        return ticketRepository.findByAtendenteId(atendenteId).stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketDTO> listarPorStatus(StatusTicket status) {
        return ticketRepository.findByStatus(status).stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TicketDTO criar(CriarTicketDTO dto, String clienteId) {
        // Buscar entidades relacionadas
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Prioridade prioridade = prioridadeRepository.findById(dto.getPrioridadeId())
                .orElseThrow(() -> new RuntimeException("Prioridade não encontrada"));

        Departamento departamento = departamentoRepository.findById(dto.getDepartamentoId())
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        SLA sla = slaRepository.findById(categoria.getSla().getId())
                .orElseThrow(() -> new RuntimeException("SLA não encontrado"));

        // Criar ticket
        Ticket ticket = ticketMapper.toEntity(dto, cliente, categoria, prioridade, departamento, sla);

        // Configurar data limite baseada no SLA
        LocalDateTime dataLimite = LocalDateTime.now().plusHours(sla.getTempoResolucao());
        ticket.setDataLimite(dataLimite);

        // Gerar código único
        String sequencial = String.format("%04d", ticketRepository.count() + 1);
        ticket.gerarCodigo(sequencial);

        Ticket ticketSalvo = ticketRepository.save(ticket);
        return ticketMapper.toDTO(ticketSalvo);
    }

    @Transactional
    public TicketDTO atualizar(String id, AtualizarTicketDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado com ID: " + id));

        Categoria categoria = null;
        if (dto.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        }

        Prioridade prioridade = null;
        if (dto.getPrioridadeId() != null) {
            prioridade = prioridadeRepository.findById(dto.getPrioridadeId())
                    .orElseThrow(() -> new RuntimeException("Prioridade não encontrada"));
        }

        Usuario atendente = null;
        if (dto.getAtendenteId() != null) {
            atendente = usuarioRepository.findById(dto.getAtendenteId())
                    .orElseThrow(() -> new RuntimeException("Atendente não encontrado"));
        }

        ticketMapper.updateEntity(ticket, dto, categoria, prioridade, atendente);
        Ticket ticketAtualizado = ticketRepository.save(ticket);

        return ticketMapper.toDTO(ticketAtualizado);
    }

    @Transactional
    public TicketDTO atribuirAtendente(String ticketId, String atendenteId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        Usuario atendente = usuarioRepository.findById(atendenteId)
                .orElseThrow(() -> new RuntimeException("Atendente não encontrado"));

        ticket.atribuirAtendente(atendente);
        Ticket ticketAtualizado = ticketRepository.save(ticket);

        return ticketMapper.toDTO(ticketAtualizado);
    }

    @Transactional
    public TicketDTO alterarStatus(String ticketId, StatusTicket status) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        ticket.alterarStatus(status);
        Ticket ticketAtualizado = ticketRepository.save(ticket);

        return ticketMapper.toDTO(ticketAtualizado);
    }

    @Transactional
    public TicketDTO adicionarTempoGasto(String ticketId, Integer minutos) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        ticket.adicionarTempoGasto(minutos);
        Ticket ticketAtualizado = ticketRepository.save(ticket);

        return ticketMapper.toDTO(ticketAtualizado);
    }

    @Transactional
    public TicketDTO avaliar(String ticketId, Integer nota, String feedback) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        ticket.avaliar(nota, feedback);
        Ticket ticketAtualizado = ticketRepository.save(ticket);

        return ticketMapper.toDTO(ticketAtualizado);
    }

    public List<TicketDTO> listarAtrasados() {
        return ticketRepository.findTicketsAtrasados(LocalDateTime.now()).stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketDTO> listarPorDepartamento(String departamentoId) {
        return ticketRepository.findByDepartamentoId(departamentoId).stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }
}