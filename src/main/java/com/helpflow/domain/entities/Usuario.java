package com.helpflow.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;

    @DBRef
    private Departamento departamento;

    @DBRef
    private Perfil perfil;

    private String avatar;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Usuario() {
        this.ativo = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Métodos de domínio
    public boolean isCliente() {
        return perfil != null && perfil.isCliente();
    }

    public boolean isAtendente() {
        return perfil != null && perfil.isAtendente();
    }

    public boolean isGestor() {
        return perfil != null && perfil.isGestor();
    }

    public void desativar() {
        this.ativo = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void ativar() {
        this.ativo = true;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean podeAcessarTicket(Ticket ticket) {
        if (this.isGestor()) return true;
        if (this.isAtendente() && ticket.getAtendente() != null) {
            return ticket.getAtendente().getId().equals(this.id);
        }
        if (this.isCliente() && ticket.getCliente() != null) {
            return ticket.getCliente().getId().equals(this.id);
        }
        return false;
    }
}