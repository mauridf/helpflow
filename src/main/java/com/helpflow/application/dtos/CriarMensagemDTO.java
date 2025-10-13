package com.helpflow.application.dtos;

import com.helpflow.domain.enums.TipoMensagem;
import lombok.Data;
import java.util.List;

@Data
public class CriarMensagemDTO {
    private String mensagem;
    private TipoMensagem tipo;
    private Boolean interna;
    private List<CriarAnexoDTO> anexos;
}