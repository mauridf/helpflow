package com.helpflow.application.mappers;

import com.helpflow.application.dtos.DepartamentoDTO;
import com.helpflow.application.dtos.CriarDepartamentoDTO;
import com.helpflow.application.dtos.AtualizarDepartamentoDTO;
import com.helpflow.domain.entities.Departamento;
import com.helpflow.domain.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class DepartamentoMapper {

    public DepartamentoDTO toDTO(Departamento departamento) {
        if (departamento == null) return null;

        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(departamento.getId());
        dto.setNome(departamento.getNome());
        dto.setDescricao(departamento.getDescricao());
        dto.setEmail(departamento.getEmail());
        dto.setAtivo(departamento.getAtivo());
        dto.setCreatedAt(departamento.getCreatedAt());
        dto.setUpdatedAt(departamento.getUpdatedAt());

        if (departamento.getResponsavel() != null) {
            dto.setResponsavelId(departamento.getResponsavel().getId());
            dto.setResponsavelNome(departamento.getResponsavel().getNome());
        }

        return dto;
    }

    public Departamento toEntity(CriarDepartamentoDTO dto, Usuario responsavel) {
        Departamento departamento = new Departamento();
        departamento.setNome(dto.getNome());
        departamento.setDescricao(dto.getDescricao());
        departamento.setEmail(dto.getEmail());
        departamento.setResponsavel(responsavel);
        return departamento;
    }

    public void updateEntity(Departamento departamento, AtualizarDepartamentoDTO dto, Usuario responsavel) {
        if (dto.getNome() != null) departamento.setNome(dto.getNome());
        if (dto.getDescricao() != null) departamento.setDescricao(dto.getDescricao());
        if (dto.getEmail() != null) departamento.setEmail(dto.getEmail());
        if (responsavel != null) departamento.setResponsavel(responsavel);

        departamento.setUpdatedAt(java.time.LocalDateTime.now());
    }
}