package com.helpflow.application.services;

import com.helpflow.application.dtos.DepartamentoDTO;
import com.helpflow.application.dtos.CriarDepartamentoDTO;
import com.helpflow.application.dtos.AtualizarDepartamentoDTO;
import com.helpflow.application.mappers.DepartamentoMapper;
import com.helpflow.domain.entities.Departamento;
import com.helpflow.domain.entities.Usuario;
import com.helpflow.infrastructure.persistence.mongodb.DepartamentoRepository;
import com.helpflow.infrastructure.persistence.mongodb.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DepartamentoMapper departamentoMapper;

    public List<DepartamentoDTO> listarTodos() {
        return departamentoRepository.findAll().stream()
                .map(departamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DepartamentoDTO> listarAtivos() {
        return departamentoRepository.findByAtivoTrue().stream()
                .map(departamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DepartamentoDTO buscarPorId(String id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + id));
        return departamentoMapper.toDTO(departamento);
    }

    public DepartamentoDTO buscarPorNome(String nome) {
        Departamento departamento = departamentoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com nome: " + nome));
        return departamentoMapper.toDTO(departamento);
    }

    @Transactional
    public DepartamentoDTO criar(CriarDepartamentoDTO dto) {
        // Validar nome único
        if (departamentoRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Já existe um departamento com este nome: " + dto.getNome());
        }

        // Buscar responsável
        Usuario responsavel = null;
        if (dto.getResponsavelId() != null) {
            responsavel = usuarioRepository.findById(dto.getResponsavelId())
                    .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
        }

        // Criar departamento
        Departamento departamento = departamentoMapper.toEntity(dto, responsavel);
        Departamento departamentoSalvo = departamentoRepository.save(departamento);

        return departamentoMapper.toDTO(departamentoSalvo);
    }

    @Transactional
    public DepartamentoDTO atualizar(String id, AtualizarDepartamentoDTO dto) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + id));

        // Validar nome único se estiver alterando
        if (dto.getNome() != null && !dto.getNome().equals(departamento.getNome())) {
            if (departamentoRepository.existsByNome(dto.getNome())) {
                throw new RuntimeException("Já existe um departamento com este nome: " + dto.getNome());
            }
        }

        // Buscar responsável
        Usuario responsavel = null;
        if (dto.getResponsavelId() != null) {
            responsavel = usuarioRepository.findById(dto.getResponsavelId())
                    .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
        }

        departamentoMapper.updateEntity(departamento, dto, responsavel);
        Departamento departamentoAtualizado = departamentoRepository.save(departamento);

        return departamentoMapper.toDTO(departamentoAtualizado);
    }

    @Transactional
    public void desativar(String id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + id));
        departamento.desativar();
        departamentoRepository.save(departamento);
    }

    @Transactional
    public void ativar(String id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + id));
        departamento.ativar();
        departamentoRepository.save(departamento);
    }
}