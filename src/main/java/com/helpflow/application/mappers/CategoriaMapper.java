package com.helpflow.application.mappers;

import com.helpflow.application.dtos.CategoriaDTO;
import com.helpflow.application.dtos.CriarCategoriaDTO;
import com.helpflow.application.dtos.AtualizarCategoriaDTO;
import com.helpflow.domain.entities.Categoria;
import com.helpflow.domain.entities.Departamento;
import com.helpflow.domain.entities.SLA;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) return null;

        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setCor(categoria.getCor());
        dto.setCreatedAt(categoria.getCreatedAt());
        dto.setUpdatedAt(categoria.getUpdatedAt());

        if (categoria.getDepartamento() != null) {
            dto.setDepartamentoId(categoria.getDepartamento().getId());
            dto.setDepartamentoNome(categoria.getDepartamento().getNome());
        }

        if (categoria.getSla() != null) {
            dto.setSlaId(categoria.getSla().getId());
            dto.setSlaNome(categoria.getSla().getNome());
        }

        return dto;
    }

    public Categoria toEntity(CriarCategoriaDTO dto, Departamento departamento, SLA sla) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDepartamento(departamento);
        categoria.setSla(sla);
        categoria.setCor(dto.getCor());
        return categoria;
    }

    public void updateEntity(Categoria categoria, AtualizarCategoriaDTO dto, Departamento departamento, SLA sla) {
        if (dto.getNome() != null) categoria.setNome(dto.getNome());
        if (dto.getCor() != null) categoria.setCor(dto.getCor());
        if (departamento != null) categoria.setDepartamento(departamento);
        if (sla != null) categoria.setSla(sla);

        categoria.setUpdatedAt(java.time.LocalDateTime.now());
    }
}