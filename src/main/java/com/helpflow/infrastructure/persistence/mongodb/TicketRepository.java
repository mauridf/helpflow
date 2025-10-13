package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Ticket;
import com.helpflow.domain.enums.StatusTicket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    Optional<Ticket> findByCodigo(String codigo);
    List<Ticket> findByClienteId(String clienteId);
    List<Ticket> findByAtendenteId(String atendenteId);
    List<Ticket> findByStatus(StatusTicket status);
    List<Ticket> findByDepartamentoId(String departamentoId);
    List<Ticket> findByPrioridadeNivel(Integer nivel);

    @Query("{ 'dataLimite': { $lt: ?0 }, 'status': { $nin: ['FECHADO', 'CANCELADO', 'RESOLVIDO'] } }")
    List<Ticket> findTicketsAtrasados(LocalDateTime agora);

    List<Ticket> findByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("{ 'categoria.id': ?0 }")
    List<Ticket> findByCategoriaId(String categoriaId);
}