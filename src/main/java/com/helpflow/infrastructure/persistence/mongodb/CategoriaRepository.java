package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria, String> {
    List<Categoria> findByDepartamentoId(String departamentoId);
    Optional<Categoria> findByNome(String nome);
    List<Categoria> findByDepartamentoIdAndNome(String departamentoId, String nome);
}