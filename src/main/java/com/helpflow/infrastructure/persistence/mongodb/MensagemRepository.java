package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Mensagem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends MongoRepository<Mensagem, String> {
    List<Mensagem> findByTicketIdOrderByCreatedAtAsc(String ticketId);
    List<Mensagem> findByTicketIdAndInternaFalse(String ticketId);

    @Query("{ 'ticket.$id': ?0, 'lida': false, 'usuario.$id': { $ne: ?1 } }")
    Long countByTicketIdAndLidaFalseAndUsuarioIdNot(String ticketId, String usuarioId);


    @Query("{ 'ticket.$id': ?0, 'lida': false, 'usuario.$id': { $ne: ?1 } }")
    List<Mensagem> findByTicketIdAndLidaFalseAndUsuarioIdNot(String ticketId, String usuarioId);


    List<Mensagem> findByUsuarioId(String usuarioId);


    Long countByTicketId(String ticketId);
}