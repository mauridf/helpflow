package com.helpflow.application.services;

import com.helpflow.application.dtos.UsuarioDTO;
import com.helpflow.application.dtos.CriarUsuarioDTO;
import com.helpflow.application.dtos.AtualizarUsuarioDTO;
import com.helpflow.application.mappers.UsuarioMapper;
import com.helpflow.domain.entities.Usuario;
import com.helpflow.domain.entities.Departamento;
import com.helpflow.domain.entities.Perfil;
import com.helpflow.infrastructure.persistence.mongodb.UsuarioRepository;
import com.helpflow.infrastructure.persistence.mongodb.DepartamentoRepository;
import com.helpflow.infrastructure.persistence.mongodb.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final DepartamentoRepository departamentoRepository;
    private final PerfilRepository perfilRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder; // CORREÇÃO: Adicionado

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
        return usuarioMapper.toDTO(usuario);
    }

    @Transactional
    public UsuarioDTO criar(CriarUsuarioDTO dto) {
        // Validar email único
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este email: " + dto.getEmail());
        }

        // Buscar departamento e perfil
        Departamento departamento = null;
        if (dto.getDepartamentoId() != null) {
            departamento = departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
        }

        Perfil perfil = perfilRepository.findById(dto.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        // Criar usuário
        Usuario usuario = usuarioMapper.toEntity(dto, departamento, perfil);
        // CORREÇÃO: Hash da senha
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioSalvo);
    }

    @Transactional
    public UsuarioDTO atualizar(String id, AtualizarUsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        Departamento departamento = null;
        if (dto.getDepartamentoId() != null) {
            departamento = departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
        }

        usuarioMapper.updateEntity(usuario, dto, departamento);
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    @Transactional
    public void desativar(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        usuario.desativar();
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void ativar(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        usuario.ativar();
        usuarioRepository.save(usuario);
    }

    public List<UsuarioDTO> listarPorPerfil(String perfilNome) {
        return usuarioRepository.findByPerfilNome(perfilNome).stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> listarAtendentes() {
        return listarPorPerfil("Atendente");
    }

    public List<UsuarioDTO> listarClientes() {
        return listarPorPerfil("Cliente");
    }
}