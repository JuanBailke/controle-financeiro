# 🔒 Controle Financeiro

Este repositório reúne o **backend** em Spring Boot e o **frontend** em Angular de um sistema simples de controle financeiro com autenticação JWT e Docker.

---

## 📂 Estrutura do Projeto


├── assets/                   # Recursos estáticos 

│   └── Diagrama.png          # Diagrama de Classes

├── backend/                  # Projeto Spring Boot 

│   ├── src/ 

│   ├── Dockerfile 

│   └── pom.xml 

├── frontend/                 # Projeto Angular 

│   ├── src/ 

│   ├── angular.json 

│   └── package.json

├── .env.example          # Exemplo de variáveis de ambiente 

└── docker-compose.yml 

---

## 📊 Diagrama de Classes

![Diagrama de Classes](https://github.com/JuanBailke/controle-financeiro/blob/main/assets/Diagrama.png)

---
## 🛠 Tecnologias

### Backend

- Java 17  
- Spring Boot 3.5.3  
- Maven  
- Spring Security + JWT  
  - Filtros customizados (`JwtFilter`)  
  - `AuthenticationEntryPoint` e `AccessDeniedHandler`  
- Spring Data JPA (Hibernate)  
- Banco de dados H2 (testes) / PostgreSQL (desenvolvimento e produção)  
- Lombok (redução de boilerplate)  
- Testes com JUnit e Mockito
- Docker + Docker Compose  

### Frontend (Planejado)

- Node.js 18 LTS  
- Angular 16+ (Standalone Components)  
- Angular CLI  
- TypeScript  
- SCSS (Sass)  
- RxJS
- Docker
- Arquitetura de Módulos  
  - `CoreModule` (serviços singleton, interceptors, guards)  
  - `SharedModule` (CommonModule, FormsModule, componentes/pipes reutilizáveis)  
  - `AuthModule` (login/logout)  
  - `DashboardModule` (rotas protegidas via `AuthGuard`)

---

## ⚙️ Configuração do ambiente
1. Copie o arquivo `.env.example` e renomeie para `.env`:
   ```sh
   cp .env.example .env
   ```
2. Preencha as variáveis com seus valores reais.
3. O `.env` **não deve ser versionado** no Git (já está no `.gitignore`).

---

## 🐳 Docker

No diretório raiz do projeto, basta:

```bash
docker-compose up --build
```

Isso irá subir:
- Backend na porta 8080
- PostgreSQL na porta 5432
- PGAdmin na porta 5050
- Frontend na porta 4200 (A ser implementado)
  
Para derrubar:
```bash
docker-compose down
```
---

## 🚀 Como Rodar Manualmente

### Backend
```bash
cd backend
mvn clean spring-boot:run
```

API disponível em `http://localhost:8080`.

### Frontend
```bash
cd frontend
npm install
ng serve
```

App disponível em `http://localhost:4200`.

---

## 🔐 Fluxo de Autenticação

- **Login**
  - POST /autenticacao/login → { accessToken, refreshToken }

- **Armazenamento**
  - Token salvo em localStorage.

- **Interceptor HTTP**
  - Adiciona Authorization: Bearer <token> em todas as requisições.

- **Guarda de Rotas**
  - AuthGuard bloqueia acesso sem token ou token expirado.

---

## 📦 API Endpoints

| Método | Rota | Descrição | Permissão | 
|--------|------|-----------|-----------|
| POST | /autenticacao/login | Autentica e retorna JWT | permitAll | 
| POST | /usuarios/register | Cria usuário (USER ou ADMIN) | permitAll | 
| GET | /transactions | Lista transações do usuário logado | isAuthenticated | 
| GET | /users/{userId}/transactions | Lista transações de um usuário | ROLE_ADMIN | 

---

## 🤝 Como Contribuir

- Fork do repositório
- Criar branch de feature:
```bash
git checkout -b feature/minha-feature
```
- Commit e push:

```bash
git commit -m "Descrição da feature"
git push origin feature/minha-feature
```
- Abra um Pull Request

---

## 📄 Licença
Este projeto está licenciado sob a MIT License. Veja o arquivo LICENSE para mais detalhes.

`Você pode ajustar detalhes (nome do banco, portas, variáveis de ambiente) e incluir links para diagramas ou storyboards conforme o projeto evolui.`
