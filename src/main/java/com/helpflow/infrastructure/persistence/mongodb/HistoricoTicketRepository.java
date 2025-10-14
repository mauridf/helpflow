package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.HistoricoTicket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoTicketRepository extends MongoRepository<HistoricoTicket, String> {
    List<HistoricoTicket> findByTicketIdOrderByCreatedAtDesc(String ticketId);
    List<HistoricoTicket> findByUsuarioId(String usuarioId);
    List<HistoricoTicket> findByAcao(String acao);
}