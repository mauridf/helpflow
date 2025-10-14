package com.helpflow.presentation.controllers;

import com.helpflow.domain.entities.Prioridade;
import com.helpflow.domain.entities.Perfil;
import com.helpflow.domain.entities.SLA;
import com.helpflow.domain.entities.Departamento;
import com.helpflow.domain.entities.Categoria;
import com.helpflow.infrastructure.persistence.mongodb.PrioridadeRepository;
import com.helpflow.infrastructure.persistence.mongodb.PerfilRepository;
import com.helpflow.infrastructure.persistence.mongodb.SLARepository;
import com.helpflow.infrastructure.persistence.mongodb.DepartamentoRepository;
import com.helpflow.infrastructure.persistence.mongodb.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/setup")
@RequiredArgsConstructor
public class SetupController {

    private final PerfilRepository perfilRepository;
    private final PrioridadeRepository prioridadeRepository;
    private final SLARepository slaRepository;
    private final DepartamentoRepository departamentoRepository;
    private final CategoriaRepository categoriaRepository;

    @GetMapping("/dados-iniciais")
    public ResponseEntity<Map<String, Object>> getDadosIniciais() {
        Map<String, Object> dados = new HashMap<>();

        dados.put("perfis", perfilRepository.findAll());
        dados.put("prioridades", prioridadeRepository.findAll());
        dados.put("slas", slaRepository.findAll());
        dados.put("departamentos", departamentoRepository.findAll());
        dados.put("categorias", categoriaRepository.findAll());

        // Dados do usuário admin para teste
        Map<String, String> admin = new HashMap<>();
        admin.put("email", "admin@helpflow.com");
        admin.put("senha", "123456");
        dados.put("admin", admin);

        return ResponseEntity.ok(dados);
    }

    @GetMapping("/perfis")
    public ResponseEntity<List<Perfil>> listarPerfis() {
        List<Perfil> perfis = perfilRepository.findAll();
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/prioridades")
    public ResponseEntity<List<Prioridade>> listarPrioridades() {
        List<Prioridade> prioridades = prioridadeRepository.findAll();
        return ResponseEntity.ok(prioridades);
    }

    @GetMapping("/slas")
    public ResponseEntity<List<SLA>> listarSLAs() {
        List<SLA> slas = slaRepository.findAll();
        return ResponseEntity.ok(slas);
    }

    @GetMapping("/departamentos")
    public ResponseEntity<List<Departamento>> listarDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        return ResponseEntity.ok(departamentos);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return ResponseEntity.ok(categorias);
    }
}