package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Perfil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends MongoRepository<Perfil, String> {
    Optional<Perfil> findByNome(String nome);
    Optional<Perfil> findByNivelAcesso(Integer nivelAcesso);
}