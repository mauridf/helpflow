🎫 HelpFlow API Sistema completo de gerenciamento de tickets de suporte
técnico desenvolvido em Java Spring Boot com arquitetura DDD e Clean
Architecture.

📋 Índice Visão Geral Funcionalidades Tecnologias Pré-requisitos
Instalação e Execução Documentação da API Autenticação Endpoints
Principais Estrutura do Projeto Exemplos de Uso Deploy Contribuição

🎯 Visão Geral O HelpFlow é uma plataforma robusta para gerenciamento de
tickets de suporte que permite:

Abertura e acompanhamento de chamados por clientes Atendimento
organizado por equipes técnicas Chat em tempo real entre clientes e
atendentes Controle de prazos baseado em SLA Dashboard analítico para
gestão Sistema completo de autenticação e autorização

✨ Funcionalidades 👥 Módulo de Usuários ✅ Registro e autenticação de
usuários ✅ Perfis de acesso (Cliente, Atendente, Gestor) ✅ Controle de
departamentos ✅ Gestão de perfis e permissões

🎫 Módulo de Tickets ✅ Abertura e acompanhamento de tickets ✅
Categorização e priorização ✅ Controle de status (Aberto, Em Andamento,
Resolvido, etc.) ✅ Sistema de SLA e prazos ✅ Avaliação e feedback

💬 Módulo de Chat ✅ Mensagens em tempo real ✅ Upload de anexos ✅
Mensagens internas entre atendentes ✅ Histórico de conversas ✅
Indicadores de mensagens lidas

📊 Módulo de Dashboard ✅ Dashboard Cliente: Tickets pessoais e
estatísticas ✅ Dashboard Atendente: Métricas de desempenho e alertas ✅
Dashboard Gestor: Visão geral e analytics ✅ Relatórios de SLA e
desempenho

🛠 Tecnologias Backend Java 17 - Linguagem de programação Spring Boot
3.2.0 - Framework principal Spring Security - Autenticação e autorização
Spring Data MongoDB - Persistência de dados JWT - Tokens de autenticação
Maven - Gerenciamento de dependências

Banco de Dados MongoDB - Banco de dados NoSQL MongoDB Driver - Conexão
com banco

Documentação e Ferramentas Swagger/OpenAPI 3 - Documentação interativa
SpringDoc - Integração Swagger com Spring Boot Lombok - Redução de
boilerplate code

Segurança BCrypt - Hash de senhas JWT - Tokens stateless CORS -
Configuração de origens

📋 Pré-requisitos Antes de executar o projeto, certifique-se de ter
instalado: Java 17 ou superior Maven 3.6 ou superior MongoDB 4.4 ou
superior Git para clonar o repositório

Verificar instalações: bash java -version mvn -version mongod --version
git --version

🚀 Instalação e Execução 1. Clonar o repositório bash git clone
https://github.com/seu-usuario/helpflow.git cd helpflow

2.  Configurar o MongoDB Certifique-se que o MongoDB está rodando: bash
    sudo systemctl start mongod net start MongoDB mongod

3.  Configurar a aplicação properties spring.data.mongodb.host=localhost
    spring.data.mongodb.port=27017 spring.data.mongodb.database=helpflow
    server.port=8080

4.  Executar a aplicação bash mvn clean spring-boot:run mvn clean
    package java -jar target/helpflow-0.0.1-SNAPSHOT.jar

5.  Verificar se está rodando http://localhost:8080/api/health

📖 Documentação da API Swagger UI http://localhost:8080/swagger-ui.html
OpenAPI JSON http://localhost:8080/v3/api-docs

🔐 Autenticação POST /api/auth/login Authorization: Bearer
seu-token-jwt-aqui

📡 Endpoints Principais Auth, Usuários, Tickets, Mensagens, Dashboard

🏗 Estrutura do Projeto helpflow/ ├── src/main/java/com/helpflow/ │ ├──
domain/ │ ├── application/ │ ├── infrastructure/ │ ├── presentation/ │
└── core/config/ ├── src/main/resources/ └── pom.xml

💡 Exemplos de Uso POST /api/usuarios POST /api/tickets POST
/api/mensagens

🚀 Deploy mvn clean package -Pprod java -jar
-Dspring.profiles.active=prod target/helpflow-0.0.1-SNAPSHOT.jar

🤝 Contribuição Fork → Branch → Commit → PR

📞 Suporte Swagger UI • Issues no GitHub • suporte@helpflow.com

📄 Licença MIT

🎊 Status Produção Ready \| Java 17 \| Spring Boot 3.2.0 \| MongoDB 5.0
