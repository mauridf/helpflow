package com.helpflow.domain.entities;

import com.helpflow.domain.enums.TipoMensagem;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Document(collection = "mensagens")
public class Mensagem {
    @Id
    private String id;

    @DBRef
    private Ticket ticket;

    @DBRef
    private Usuario usuario;

    private String mensagem;
    private TipoMensagem tipo;
    private Boolean interna;
    private Boolean lida;
    private LocalDateTime createdAt;

    public Mensagem() {
        this.interna = false;
        this.lida = false;
        this.tipo = TipoMensagem.TEXTO;
        this.createdAt = LocalDateTime.now();
    }

    // Métodos de domínio
    public void marcarComoLida() {
        this.lida = true;
    }

    public boolean isVisivelParaCliente() {
        return !interna;
    }

    public boolean isMensagemSistema() {
        return tipo == TipoMensagem.SISTEMA;
    }
}