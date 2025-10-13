package com.helpflow.core.config;

import com.helpflow.domain.entities.Prioridade;
import com.helpflow.domain.entities.Perfil;
import com.helpflow.domain.entities.SLA;
import com.helpflow.infrastructure.persistence.mongodb.PrioridadeRepository;
import com.helpflow.infrastructure.persistence.mongodb.PerfilRepository;
import com.helpflow.infrastructure.persistence.mongodb.SLARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PerfilRepository perfilRepository;
    private final PrioridadeRepository prioridadeRepository;
    private final SLARepository slaRepository;

    @Override
    public void run(String... args) throws Exception {
        initializePerfis();
        initializePrioridades();
        initializeSLAs();
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
}