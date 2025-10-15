package com.helpflow.core.config;

import com.helpflow.domain.entities.*;
import com.helpflow.infrastructure.persistence.mongodb.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PerfilRepository perfilRepository;
    private final PrioridadeRepository prioridadeRepository;
    private final SLARepository slaRepository;
    private final DepartamentoRepository departamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializePerfis();
        initializePrioridades();
        initializeSLAs();
        initializeDepartamentos();
        initializeCategorias();
        initializeUsuarios();
    }

    private void initializeUsuarios() {
        if (usuarioRepository.count() == 0) {
            Perfil perfilGestor = perfilRepository.findByNome("Gestor")
                    .orElseThrow(() -> new RuntimeException("Perfil Gestor não encontrado"));

            Usuario admin = new Usuario();
            admin.setNome("Administrador Sistema");
            admin.setEmail("admin@helpflow.com");
            admin.setSenha(passwordEncoder.encode("123456"));
            admin.setPerfil(perfilGestor);
            usuarioRepository.save(admin);

            System.out.println("✅ Usuário admin criado com sucesso!");
        }
    }

    private void initializePerfis() {
        if (perfilRepository.count() == 0) {
            perfilRepository.saveAll(Arrays.asList(
                    new Perfil("Cliente", 1),
                    new Perfil("Atendente", 2),
                    new Perfil("Gestor", 3)
            ));
            System.out.println("✅ Perfis inicializados com sucesso!");
        }
    }

    private void initializePrioridades() {
        if (prioridadeRepository.count() == 0) {
            prioridadeRepository.saveAll(Arrays.asList(
                    new Prioridade("Baixa", 1, "#22C55E", "arrow-down"),
                    new Prioridade("Média", 2, "#EAB308", "minus"),
                    new Prioridade("Alta", 3, "#F97316", "arrow-up"),
                    new Prioridade("Urgente", 4, "#EF4444", "alert-triangle")
            ));
            System.out.println("✅ Prioridades inicializadas com sucesso!");
        }
    }

    private void initializeSLAs() {
        if (slaRepository.count() == 0) {
            slaRepository.saveAll(Arrays.asList(
                    new SLA("SLA Básico", 60, 24, "SLA para questões rotineiras"),
                    new SLA("SLA Urgente", 15, 4, "SLA para questões críticas"),
                    new SLA("SLA Corporativo", 30, 8, "SLA para clientes corporativos")
            ));
            System.out.println("✅ SLAs inicializados com sucesso!");
        }
    }

    private void initializeDepartamentos() {
        if (departamentoRepository.count() == 0) {
            // Buscar um gestor para ser responsável
            Perfil perfilGestor = perfilRepository.findByNome("Gestor")
                    .orElseThrow(() -> new RuntimeException("Perfil Gestor não encontrado"));

            // ✅ CORREÇÃO: Usar o usuário admin já criado ou criar com senha codificada
            Usuario gestor = usuarioRepository.findByEmail("admin@helpflow.com")
                    .orElseGet(() -> {
                        Usuario admin = new Usuario();
                        admin.setNome("Administrador Sistema");
                        admin.setEmail("admin@helpflow.com");
                        admin.setSenha(passwordEncoder.encode("123456")); // ✅ Senha codificada
                        admin.setPerfil(perfilGestor);
                        return usuarioRepository.save(admin);
                    });

            departamentoRepository.saveAll(Arrays.asList(
                    criarDepartamento("Suporte Técnico", "Atendimento a problemas técnicos", "suporte@helpflow.com", gestor),
                    criarDepartamento("Comercial", "Vendas e informações comerciais", "comercial@helpflow.com", gestor),
                    criarDepartamento("Financeiro", "Cobrança e questões financeiras", "financeiro@helpflow.com", gestor)
            ));
            System.out.println("✅ Departamentos inicializados com sucesso!");
        }
    }

    private void initializeCategorias() {
        if (categoriaRepository.count() == 0) {
            // Buscar departamento e SLA
            Departamento suporte = departamentoRepository.findByNome("Suporte Técnico")
                    .orElseThrow(() -> new RuntimeException("Departamento Suporte não encontrado"));

            SLA slaBasico = slaRepository.findByNome("SLA Básico")
                    .orElseThrow(() -> new RuntimeException("SLA Básico não encontrado"));

            SLA slaUrgente = slaRepository.findByNome("SLA Urgente")
                    .orElseThrow(() -> new RuntimeException("SLA Urgente não encontrado"));

            categoriaRepository.saveAll(Arrays.asList(
                    criarCategoria("Problemas de Login", suporte, slaBasico, "#3B82F6"),
                    criarCategoria("Erros no Sistema", suporte, slaUrgente, "#EF4444"),
                    criarCategoria("Dúvidas de Uso", suporte, slaBasico, "#10B981"),
                    criarCategoria("Solicitações de Melhoria", suporte, slaBasico, "#8B5CF6")
            ));
            System.out.println("✅ Categorias inicializadas com sucesso!");
        }
    }

    // Métodos auxiliares
    private Departamento criarDepartamento(String nome, String descricao, String email, Usuario responsavel) {
        Departamento departamento = new Departamento();
        departamento.setNome(nome);
        departamento.setDescricao(descricao);
        departamento.setEmail(email);
        departamento.setResponsavel(responsavel);
        return departamento;
    }

    private Categoria criarCategoria(String nome, Departamento departamento, SLA sla, String cor) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDepartamento(departamento);
        categoria.setSla(sla);
        categoria.setCor(cor);
        return categoria;
    }
}