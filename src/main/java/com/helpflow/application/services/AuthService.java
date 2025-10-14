package com.helpflow.application.services;

import com.helpflow.application.dtos.LoginDTO;
import com.helpflow.application.dtos.TokenDTO;
import com.helpflow.domain.entities.Usuario;
import com.helpflow.infrastructure.persistence.mongodb.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenDTO login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        if (!usuario.getAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }

        String token = jwtService.generateToken(usuario);
        String perfil = usuario.getPerfil() != null ? usuario.getPerfil().getNome() : "Cliente";

        return new TokenDTO(token, "Bearer", usuario.getId(), usuario.getNome(), perfil);
    }

    public void alterarSenha(String usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    public JwtService getJwtService() {
        return jwtService;
    }
}