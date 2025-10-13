package com.helpflow.application.mappers;

import com.helpflow.application.dtos.UsuarioDTO;
import com.helpflow.application.dtos.CriarUsuarioDTO;
import com.helpflow.application.dtos.AtualizarUsuarioDTO;
import com.helpflow.domain.entities.Usuario;
import com.helpflow.domain.entities.Departamento;
import com.helpflow.domain.entities.Perfil;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setAvatar(usuario.getAvatar());
        dto.setAtivo(usuario.getAtivo());
        dto.setCreatedAt(usuario.getCreatedAt());

        // Dados relacionados
        if (usuario.getDepartamento() != null) {
            dto.setDepartamentoId(usuario.getDepartamento().getId());
            dto.setDepartamentoNome(usuario.getDepartamento().getNome());
        }

        if (usuario.getPerfil() != null) {
            dto.setPerfilId(usuario.getPerfil().getId());
            dto.setPerfilNome(usuario.getPerfil().getNome());
        }

        return dto;
    }

    public Usuario toEntity(CriarUsuarioDTO dto, Departamento departamento, Perfil perfil) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha()); // Em produção, criptografar!
        usuario.setTelefone(dto.getTelefone());
        usuario.setDepartamento(departamento);
        usuario.setPerfil(perfil);
        return usuario;
    }

    public void updateEntity(Usuario usuario, AtualizarUsuarioDTO dto, Departamento departamento) {
        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());
        if (dto.getAvatar() != null) usuario.setAvatar(dto.getAvatar());
        if (departamento != null) usuario.setDepartamento(departamento);
    }
}