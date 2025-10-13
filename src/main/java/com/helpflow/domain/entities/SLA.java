package com.helpflow.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "slas")
public class SLA {
    @Id
    private String id;

    private String nome;
    private Integer tempoResposta; // em minutos
    private Integer tempoResolucao; // em horas
    private String descricao;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Métodos de domínio
    public boolean isTempoRespostaExcedido(long minutosDecorridos) {
        return minutosDecorridos > this.tempoResposta;
    }

    public boolean isTempoResolucaoExcedido(long horasDecorridas) {
        return horasDecorridas > this.tempoResolucao;
    }
}