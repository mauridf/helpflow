package com.helpflow.presentation.controllers;

import com.helpflow.application.dtos.DashboardAtendenteDTO;
import com.helpflow.application.dtos.DashboardClienteDTO;
import com.helpflow.application.dtos.DashboardGestorDTO;
import com.helpflow.application.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<DashboardClienteDTO> getDashboardCliente(@PathVariable String clienteId) {
        DashboardClienteDTO dashboard = dashboardService.getDashboardCliente(clienteId);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/atendente/{atendenteId}")
    public ResponseEntity<DashboardAtendenteDTO> getDashboardAtendente(@PathVariable String atendenteId) {
        DashboardAtendenteDTO dashboard = dashboardService.getDashboardAtendente(atendenteId);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/gestor")
    public ResponseEntity<DashboardGestorDTO> getDashboardGestor() {
        DashboardGestorDTO dashboard = dashboardService.getDashboardGestor();
        return ResponseEntity.ok(dashboard);
    }

    // Endpoint único que detecta automaticamente o tipo de usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getDashboardUsuario(@PathVariable String usuarioId) {
        // Em uma implementação real, buscaríamos o perfil do usuário
        // Por enquanto, retornamos um exemplo baseado no ID
        if (usuarioId.contains("cliente")) {
            return ResponseEntity.ok(dashboardService.getDashboardCliente(usuarioId));
        } else if (usuarioId.contains("atendente")) {
            return ResponseEntity.ok(dashboardService.getDashboardAtendente(usuarioId));
        } else {
            return ResponseEntity.ok(dashboardService.getDashboardGestor());
        }
    }
}