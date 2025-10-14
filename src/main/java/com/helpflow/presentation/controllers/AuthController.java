package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.LoginDTO;
import com.helpflow.application.dtos.TokenDTO;
import com.helpflow.application.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        TokenDTO token = authService.login(loginDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/alterar-senha")
    public ResponseEntity<Void> alterarSenha(
            @RequestHeader("Authorization") String token,
            @RequestParam String senhaAtual,
            @RequestParam String novaSenha) {

        String jwt = token.substring(7);
        String usuarioId = authService.getJwtService().getUserIdFromToken(jwt);

        authService.alterarSenha(usuarioId, senhaAtual, novaSenha);
        return ResponseEntity.ok().build();
    }
}