# 🎫 HelpFlow API

Sistema completo de gerenciamento de tickets de suporte técnico desenvolvido em **Java Spring Boot** com arquitetura **DDD** e **Clean Architecture**.

---

## 📋 Índice
- [Visão Geral](#-visão-geral)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Execução](#-instalação-e-execução)
- [Documentação da API](#-documentação-da-api)
- [Autenticação](#-autenticação)
- [Endpoints Principais](#-endpoints-principais)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Exemplos de Uso](#-exemplos-de-uso)
- [Deploy](#-deploy)
- [Contribuição](#-contribuição)
- [Suporte](#-suporte)
- [Licença](#-licença)
- [Status do Projeto](#-status-do-projeto)

---

## 🎯 Visão Geral

O **HelpFlow** é uma plataforma robusta para gerenciamento de tickets de suporte que permite:

- Abertura e acompanhamento de chamados por clientes  
- Atendimento organizado por equipes técnicas  
- Chat em tempo real entre clientes e atendentes  
- Controle de prazos baseado em SLA  
- Dashboard analítico para gestão  
- Sistema completo de autenticação e autorização  

---

## ✨ Funcionalidades

### 👥 Módulo de Usuários
- ✅ Registro e autenticação de usuários
- ✅ Perfis de acesso (Cliente, Atendente, Gestor)
- ✅ Controle de departamentos
- ✅ Gestão de perfis e permissões

### 🎫 Módulo de Tickets
- ✅ Abertura e acompanhamento de tickets
- ✅ Categorização e priorização
- ✅ Controle de status (Aberto, Em Andamento, Resolvido, etc.)
- ✅ Sistema de SLA e prazos
- ✅ Avaliação e feedback

### 💬 Módulo de Chat
- ✅ Mensagens em tempo real
- ✅ Upload de anexos
- ✅ Mensagens internas entre atendentes
- ✅ Histórico de conversas
- ✅ Indicadores de mensagens lidas

### 📊 Módulo de Dashboard
- ✅ Dashboard Cliente: Tickets pessoais e estatísticas
- ✅ Dashboard Atendente: Métricas de desempenho e alertas
- ✅ Dashboard Gestor: Visão geral e analytics
- ✅ Relatórios de SLA e desempenho

---

## 🛠 Tecnologias

### Backend
- Java 17  
- Spring Boot 3.2.0  
- Spring Security  
- Spring Data MongoDB  
- JWT  
- Maven  

### Banco de Dados
- MongoDB  
- MongoDB Driver  

### Documentação e Ferramentas
- Swagger/OpenAPI 3  
- SpringDoc  
- Lombok  

### Segurança
- BCrypt  
- JWT  
- CORS  

---

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

```bash
java -version
mvn -version
mongod --version
git --version
```

---

## 🚀 Instalação e Execução

### 1. Clonar o repositório
```bash
git clone https://github.com/seu-usuario/helpflow.git
cd helpflow
```

### 2. Configurar o MongoDB
```bash
# Linux/Mac
sudo systemctl start mongod

# Windows
net start MongoDB
```

### 3. Configurar a aplicação
```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=helpflow
server.port=8080
```

### 4. Executar a aplicação
```bash
mvn clean spring-boot:run
# ou
mvn clean package
java -jar target/helpflow-0.0.1-SNAPSHOT.jar
```

### 5. Verificar se está rodando
Acesse: [http://localhost:8080/api/health](http://localhost:8080/api/health)

```json
{
  "status": "UP",
  "message": "HelpFlow API is running",
  "timestamp": "2024-01-15T10:00:00"
}
```

---

## 📖 Documentação da API

### Swagger UI
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### OpenAPI JSON
[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Características:
- ✅ Interface interativa  
- ✅ Teste de endpoints diretamente no navegador  
- ✅ Schemas detalhados  
- ✅ Suporte a autenticação JWT  

---

## 🔐 Autenticação

### 1. Obter Token JWT
**POST** `/api/auth/login`

```json
{
  "email": "admin@helpflow.com",
  "senha": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "usuarioId": "507f1f77bcf86cd799439011",
  "usuarioNome": "Administrador Sistema",
  "perfil": "Gestor"
}
```

### 2. Usar Token nas Requisições
```text
Authorization: Bearer seu-token-jwt-aqui
```

---

## 📡 Endpoints Principais

### 🔐 Autenticação
| Método | Endpoint | Descrição |
|--------|-----------|------------|
| POST | `/api/auth/login` | Login de usuário |
| POST | `/api/auth/alterar-senha` | Alterar senha |

### 👥 Usuários
| Método | Endpoint | Descrição |
|--------|-----------|------------|
| GET | `/api/usuarios` | Listar todos usuários |
| POST | `/api/usuarios` | Criar usuário |
| GET | `/api/usuarios/{id}` | Buscar usuário por ID |
| PUT | `/api/usuarios/{id}` | Atualizar usuário |
| GET | `/api/usuarios/atendentes` | Listar atendentes |

### 🎫 Tickets
| Método | Endpoint | Descrição |
|--------|-----------|------------|
| GET | `/api/tickets` | Listar todos tickets |
| POST | `/api/tickets` | Criar ticket |
| GET | `/api/tickets/{id}` | Buscar ticket por ID |
| PUT | `/api/tickets/{id}` | Atualizar ticket |
| PATCH | `/api/tickets/{id}/status` | Alterar status |
| PATCH | `/api/tickets/{id}/atribuir-atendente` | Atribuir atendente |

---

## 🏗 Estrutura do Projeto

```text
helpflow/
├── src/main/java/com/helpflow/
│   ├── domain/
│   │   ├── entities/
│   │   ├── enums/
│   │   └── value-objects/
│   ├── application/
│   │   ├── dtos/
│   │   ├── mappers/
│   │   └── services/
│   ├── infrastructure/
│   │   └── persistence/
│   ├── presentation/
│   └── core/config/
├── src/main/resources/
│   ├── application.properties
│   └── static/
└── pom.xml
```

---

## 💡 Exemplos de Uso

### Criar Usuário
```bash
POST /api/usuarios
Authorization: Bearer seu-token
Content-Type: application/json
```

```json
{
  "nome": "João Silva",
  "email": "joao@empresa.com",
  "senha": "123456",
  "telefone": "(11) 99999-9999",
  "perfilId": "id-do-perfil-cliente"
}
```

### Abrir Ticket
```bash
POST /api/tickets?clienteId=id-do-cliente
```

---

## 🚀 Deploy

### 1. Configurações de Produção
```properties
spring.data.mongodb.uri=mongodb://usuario:senha@servidor:27017/helpflow
server.port=8080
spring.profiles.active=prod
spring.security.jwt.secret=seu-secret-super-seguro
```

### 2. Build e Execução
```bash
mvn clean package -Pprod
java -jar -Dspring.profiles.active=prod target/helpflow-0.0.1-SNAPSHOT.jar
```

### 3. Docker (Opcional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/helpflow-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

---

## 🤝 Contribuição

1. Fork o projeto  
2. Crie uma branch: `git checkout -b feature/NovaFeature`  
3. Commit: `git commit -m "Add NovaFeature"`  
4. Push: `git push origin feature/NovaFeature`  
5. Abra um Pull Request  

---

## 📞 Suporte

- Documentação: Swagger UI  
- Issues: GitHub  
- Email: suporte@helpflow.com  

---

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo `LICENSE` para mais detalhes.

---

## 🎊 Status do Projeto

![Status](https://img.shields.io/badge/Status-Produção%20Ready-success)
![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![MongoDB](https://img.shields.io/badge/MongoDB-5.0-green)

---
**Desenvolvido com ❤️ por Maurício Carvalho Developer**  
*Última atualização: Outubro 2025*
