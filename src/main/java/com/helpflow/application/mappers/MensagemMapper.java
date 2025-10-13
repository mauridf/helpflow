package com.helpflow.application.mappers;

import com.helpflow.application.dtos.MensagemDTO;
import com.helpflow.application.dtos.CriarMensagemDTO;
import com.helpflow.application.dtos.AtualizarMensagemDTO;
import com.helpflow.application.dtos.AnexoDTO;
import com.helpflow.domain.entities.Mensagem;
import com.helpflow.domain.entities.Ticket;
import com.helpflow.domain.entities.Usuario;
import com.helpflow.domain.entities.Anexo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MensagemMapper {

    public MensagemDTO toDTO(Mensagem mensagem) {
        if (mensagem == null) return null;

        MensagemDTO dto = new MensagemDTO();
        dto.setId(mensagem.getId());
        dto.setMensagem(mensagem.getMensagem());
        dto.setTipo(mensagem.getTipo());
        dto.setInterna(mensagem.getInterna());
        dto.setLida(mensagem.getLida());
        dto.setCreatedAt(mensagem.getCreatedAt());

        if (mensagem.getTicket() != null) {
            dto.setTicketId(mensagem.getTicket().getId());
        }

        if (mensagem.getUsuario() != null) {
            dto.setUsuarioId(mensagem.getUsuario().getId());
            dto.setUsuarioNome(mensagem.getUsuario().getNome());
            dto.setUsuarioAvatar(mensagem.getUsuario().getAvatar());
        }

        return dto;
    }

    public MensagemDTO toDTOWithAnexos(Mensagem mensagem, List<Anexo> anexos) {
        MensagemDTO dto = toDTO(mensagem);
        if (dto != null && anexos != null) {
            List<AnexoDTO> anexosDTO = anexos.stream()
                    .map(this::toAnexoDTO)
                    .collect(Collectors.toList());
            dto.setAnexos(anexosDTO);
        }
        return dto;
    }

    public Mensagem toEntity(CriarMensagemDTO dto, Ticket ticket, Usuario usuario) {
        Mensagem mensagem = new Mensagem();
        mensagem.setTicket(ticket);
        mensagem.setUsuario(usuario);
        mensagem.setMensagem(dto.getMensagem());
        mensagem.setTipo(dto.getTipo() != null ? dto.getTipo() : com.helpflow.domain.enums.TipoMensagem.TEXTO);
        mensagem.setInterna(dto.getInterna() != null ? dto.getInterna() : false);
        return mensagem;
    }

    public void updateEntity(Mensagem mensagem, AtualizarMensagemDTO dto) {
        if (dto.getMensagem() != null) mensagem.setMensagem(dto.getMensagem());
        if (dto.getLida() != null) mensagem.setLida(dto.getLida());
    }

    private AnexoDTO toAnexoDTO(Anexo anexo) {
        if (anexo == null) return null;

        AnexoDTO dto = new AnexoDTO();
        dto.setId(anexo.getId());
        dto.setNomeArquivo(anexo.getNomeArquivo());
        dto.setCaminho(anexo.getCaminho());
        dto.setTipo(anexo.getTipo());
        dto.setTamanho(anexo.getTamanho());
        dto.setTamanhoFormatado(anexo.getTamanhoFormatado());
        dto.setIsImagem(anexo.isImagem());
        dto.setIsDocumento(anexo.isDocumento());
        dto.setCreatedAt(anexo.getCreatedAt());

        if (anexo.getMensagem() != null) {
            dto.setMensagemId(anexo.getMensagem().getId());
        }

        return dto;
    }
}