package com.helpflow.infrastructure.persistence.mongodb;

import com.helpflow.domain.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByDepartamentoId(String departamentoId);
    @Query("{ 'perfil.$id': ?0 }")
    List<Usuario> findByPerfilId(String perfilId);

    @Query("{ 'perfil.nome': ?0 }")
    List<Usuario> findByPerfilNome(String perfilNome);
    List<Usuario> findByAtivoTrue();
    boolean existsByEmail(String email);
}