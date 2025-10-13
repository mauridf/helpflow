package com.helpflow.application.dtos;

import com.helpflow.domain.enums.TipoMensagem;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MensagemDTO {
    private String id;
    private String ticketId;
    private String usuarioId;
    private String usuarioNome;
    private String usuarioAvatar;
    private String mensagem;
    private TipoMensagem tipo;
    private Boolean interna;
    private Boolean lida;
    private LocalDateTime createdAt;
    private List<AnexoDTO> anexos;
}