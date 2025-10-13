package com.helpflow.application.services;

import com.helpflow.application.dtos.CategoriaDTO;
import com.helpflow.application.dtos.CriarCategoriaDTO;
import com.helpflow.application.dtos.AtualizarCategoriaDTO;
import com.helpflow.application.mappers.CategoriaMapper;
import com.helpflow.domain.entities.Categoria;
import com.helpflow.domain.entities.Departamento;
import com.helpflow.domain.entities.SLA;
import com.helpflow.infrastructure.persistence.mongodb.CategoriaRepository;
import com.helpflow.infrastructure.persistence.mongodb.DepartamentoRepository;
import com.helpflow.infrastructure.persistence.mongodb.SLARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final DepartamentoRepository departamentoRepository;
    private final SLARepository slaRepository;
    private final CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> listarTodos() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CategoriaDTO> listarPorDepartamento(String departamentoId) {
        return categoriaRepository.findByDepartamentoId(departamentoId).stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO buscarPorId(String id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
        return categoriaMapper.toDTO(categoria);
    }

    public CategoriaDTO buscarPorNome(String nome) {
        Categoria categoria = categoriaRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com nome: " + nome));
        return categoriaMapper.toDTO(categoria);
    }

    @Transactional
    public CategoriaDTO criar(CriarCategoriaDTO dto) {
        // Buscar departamento
        Departamento departamento = departamentoRepository.findById(dto.getDepartamentoId())
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        // Buscar SLA
        SLA sla = slaRepository.findById(dto.getSlaId())
                .orElseThrow(() -> new RuntimeException("SLA não encontrado"));

        // Validar nome único no departamento
        if (categoriaRepository.findByDepartamentoIdAndNome(departamento.getId(), dto.getNome()).size() > 0) {
            throw new RuntimeException("Já existe uma categoria com este nome no departamento");
        }

        // Criar categoria
        Categoria categoria = categoriaMapper.toEntity(dto, departamento, sla);
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(categoriaSalva);
    }

    @Transactional
    public CategoriaDTO atualizar(String id, AtualizarCategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));

        // Buscar departamento se estiver alterando
        Departamento departamento = null;
        if (dto.getDepartamentoId() != null) {
            departamento = departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
        }

        // Buscar SLA se estiver alterando
        SLA sla = null;
        if (dto.getSlaId() != null) {
            sla = slaRepository.findById(dto.getSlaId())
                    .orElseThrow(() -> new RuntimeException("SLA não encontrado"));
        }

        // Validar nome único se estiver alterando
        if (dto.getNome() != null && !dto.getNome().equals(categoria.getNome())) {
            if (departamento != null) {
                if (categoriaRepository.findByDepartamentoIdAndNome(departamento.getId(), dto.getNome()).size() > 0) {
                    throw new RuntimeException("Já existe uma categoria com este nome no departamento");
                }
            }
        }

        categoriaMapper.updateEntity(categoria, dto, departamento, sla);
        Categoria categoriaAtualizada = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(categoriaAtualizada);
    }

    @Transactional
    public void excluir(String id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada com ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}