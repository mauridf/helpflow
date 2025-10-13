package com.helpflow.presentation.controllers;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test/mongo")
public class MongoTestController {

    private final MongoTemplate mongoTemplate;

    public MongoTestController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/connection")
    public ResponseEntity<Map<String, Object>> testConnection() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Tenta executar um comando simples no MongoDB
            String result = mongoTemplate.executeCommand("{ ping: 1 }").toJson();
            response.put("status", "SUCCESS");
            response.put("message", "Conexão com MongoDB estabelecida com sucesso!");
            response.put("database", mongoTemplate.getDb().getName());
            response.put("ping", result);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Falha na conexão com MongoDB: " + e.getMessage());
            response.put("suggestion", "Verifique se o MongoDB está rodando na porta 27017");
            return ResponseEntity.status(500).body(response);
        }
    }
}