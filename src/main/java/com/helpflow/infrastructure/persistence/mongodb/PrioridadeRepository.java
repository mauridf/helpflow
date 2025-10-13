package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Prioridade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrioridadeRepository extends MongoRepository<Prioridade, String> {
    Optional<Prioridade> findByNivel(Integer nivel);
    Optional<Prioridade> findByNome(String nome);
}