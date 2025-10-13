package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Mensagem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends MongoRepository<Mensagem, String> {
    List<Mensagem> findByTicketIdOrderByCreatedAtAsc(String ticketId);
    List<Mensagem> findByTicketIdAndInternaFalse(String ticketId);
    Long countByTicketIdAndLidaFalseAndUsuarioIdNot(String ticketId, String usuarioId);
}