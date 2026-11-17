# Guia Geral do Projeto — SmartCash

O **SmartCash** é uma API REST para controle financeiro pessoal desenvolvida em **Java 17** com **Spring Boot 3.4.4**.

---

## 1. Stack Tecnológica

Conforme configurado no `pom.xml`:
- **Linguagem / Framework:** Java 17, Spring Boot 3.4.4 (Web, Data JPA, Validation, Security)
- **Autenticação:** JWT (`io.jsonwebtoken:jjwt` 0.12.3) com *access token* e *refresh token* (stateless)
- **Banco de Dados:** H2 (`com.h2database:h2` em runtime no `pom.xml`)
  - *Nota:* o `README.md` e o `src/main/resources/application-example.properties` referenciam MySQL e Flyway (`src/main/resources/db/migration/`), porém o driver do MySQL e a dependência do Flyway não estão declarados no `pom.xml` atual.
- **Documentação da API:** SpringDoc OpenAPI (`springdoc-openapi-starter-webmvc-ui` 2.8.8)
- **Exportação de Relatórios:** iText 7 (`com.itextpdf:itext7-core` 7.2.5) para geração de PDF
- **Utilitários:** Lombok 1.18.30, Spring Boot DevTools

---

## 2. Arquitetura e Organização

O projeto segue **Arquitetura Hexagonal (*Ports & Adapters*)** organizada por **módulo/domínio** (`user` e `spent`) + infraestrutura transversal (`infra`):

```text
src/main/java/fioshi/com/github/SmartCash/
├── user/                  # Arquitetura Hexagonal (Ports & Adapters)
│   ├── domain/model/      # Modelo de domínio puro (User), sem Spring/JPA
│   ├── application/
│   │   ├── port/in/       # Casos de uso (SignUp, Authenticate, RefreshToken)
│   │   ├── port/out/      # Portas de persistência, hash, JWT e autenticação
│   │   └── usecase/       # Implementações dos casos de uso e validações
│   └── adapter/
│       ├── in/web/        # Controllers REST e DTOs de entrada/saída
│       └── out/           # Adaptadores JPA (persistence/) e Spring Security/JWT (security/)
│
├── spent/                 # Arquitetura Hexagonal (Ports & Adapters)
│   ├── domain/model/      # Modelos e enums puros (Spent, MonthlyExpense, AdditionalIncome, Type, SpentCategorie)
│   ├── application/
│   │   ├── port/in/       # Casos de uso de gastos, rendas extras e exportação PDF
│   │   ├── port/out/      # Portas de persistência, lookup de usuário, transações e geração de PDF
│   │   └── usecase/       # Implementações dos casos de uso e validações
│   └── adapter/
│       ├── in/web/        # SpentController, AdditionalIncomeController e DTOs REST
│       └── out/           # Adaptadores JPA (persistence/) e geração de PDF com iText7 (pdf/)
│
└── infra/                 # Infraestrutura transversal
    ├── config/            # UserBeanConfig e SpentBeanConfig (wiring hexagonal)
    ├── exception/         # BusinessException e ExceptionHandlerApi
    └── security/          # SecurityConfig, JwtUtil, JwtAuthenticationFilter, UserDetailsServiceImpl
```

> Para detalhes sobre a função de cada camada e pacote na Arquitetura Hexagonal, consulte [arquitetura-hexagonal.md](./arquitetura-hexagonal.md).

---

## 3. Principais Funcionalidades e Endpoints

### Autenticação e Usuários (Rotas Públicas)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/user/signup` | Cadastro de usuário (valida CPF único/válido e e-mail único; senha criptografada com BCrypt) |
| `POST` | `/api/auth/login` | Autenticação com e-mail e senha; retorna `token` e `refreshToken` JWT |
| `POST` | `/api/auth/refresh` | Geração de novo par de tokens a partir do `refreshToken` |

### Gastos (`api/spent` — Requer Bearer JWT)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/spent/transactions` | Cadastra gasto (à vista, parcelado ou recorrente mensal) |
| `GET` | `/api/spent/transactions?userId={id}` | Lista resumos mensais do usuário |
| `GET` | `/api/spent/transactions/resume?userId={id}&month={MONTH}&year={year}` | Resumo de um mês/ano específico |
| `GET` | `/api/spent/transactions/detail?userId={id}&spentId={id}` | Detalhe de um gasto específico |
| `GET` | `/api/spent/transactions/categories` | Lista categorias disponíveis |
| `PATCH` | `/api/spent/transactions/update/{id}` | Atualiza dados de um gasto |
| `DELETE` | `/api/spent/transactions/delete/{id}` | Remove um gasto |
| `GET` | `/api/spent/transactions/export/pdf?userId={id}` | Exporta relatório completo em PDF |
| `GET` | `/api/spent/transactions/export/pdf/month?userId={id}&month={MONTH}&year={year}` | Exporta relatório mensal em PDF |

### Renda Adicional (`/api/additional-income` — Requer Bearer JWT)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/additional-income/{userId}` | Cadastra renda adicional para um mês/ano |
| `GET` | `/api/additional-income/{userId}` | Lista rendas adicionais do usuário |
| `DELETE` | `/api/additional-income/{id}` | Remove uma renda adicional |

### Documentação Interativa (Swagger)
Com a aplicação em execução, as rotas públicas da documentação ficam disponíveis em:
- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/v3/api-docs`

---

## 4. Como Executar Localmente

1. **Configurar propriedades da aplicação:**
   Crie o arquivo `src/main/resources/application.properties` (usando `src/main/resources/application-example.properties` como referência).
   - Se for usar **H2** (já presente no `pom.xml`), configure o datasource H2.
   - Se for usar **MySQL** (como no arquivo de exemplo), adicione a dependência `mysql-connector-j` no `pom.xml` e configure usuário/senha no `application.properties`.
2. **Compilar e rodar testes:**
   ```bash
   ./mvnw test
   ```
3. **Subir a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```
