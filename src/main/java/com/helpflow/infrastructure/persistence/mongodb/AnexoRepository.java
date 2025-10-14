package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Anexo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnexoRepository extends MongoRepository<Anexo, String> {
    List<Anexo> findByMensagemId(String mensagemId);
    void deleteByMensagemId(String mensagemId);
    @Query("{ 'mensagem.ticket.$id': ObjectId(?0) }")
    List<Anexo> findByMensagemTicketId(String ticketId);
}