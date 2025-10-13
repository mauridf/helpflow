package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Departamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentoRepository extends MongoRepository<Departamento, String> {
    List<Departamento> findByAtivoTrue();
    Optional<Departamento> findByNome(String nome);
    boolean existsByNome(String nome);
}