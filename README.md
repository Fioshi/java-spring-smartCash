# 🚧 SmartCash - API em Desenvolvimento 🚧

Este repositório contém a API do **SmartCash**, um sistema de controle financeiro pessoal. A aplicação ainda está em fase de desenvolvimento ativo, e mudanças podem ocorrer com frequência.

---

## 🧰 Tecnologias Principais

- **Spring Boot 3.4.4** - Framework principal para construção da API.
- **Spring Data JPA** - Abstração poderosa para persistência com banco de dados.
- **MySQL** - Banco de dados relacional utilizado no ambiente local.
- **Flyway** - Controle de versionamento de migrações no banco de dados.
- **Spring Security** (em desenvolvimento) - Para futura implementação de autenticação/autorizacão segura.
- **Swagger/OpenAPI 3** - Documentação interativa da API, acessível via navegador.

---

## 📁 Documentação da API (Swagger)

Após rodar a aplicação, acesse a URL:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🗄️ Migrations com Flyway

As migrações SQL são aplicadas automaticamente ao subir a aplicação. As versões estão localizadas em:  
`src/main/resources/db/migration`

---

## 🚀 Como executar localmente

```bash
# Clonar o repositório
git clone https://github.com/Fioshi/java-spring-smartCash.git
cd SmartCash

# Build do projeto
./mvnw clean install

# Rodar a aplicação
./mvnw spring-boot:run
```

Certifique-se de que o MySQL está rodando e o banco está configurado corretamente no `application.properties`.

---

## Diagrama do Banco de Dados

```mermaid
erDiagram
    tb_user ||--o{ tb_spent : "possui"
    tb_user ||--o{ tb_monthly_expense : "relacionado"
    tb_monthly_expense ||--o{ tb_monthly_expense_spents : "associa"
    tb_spent ||--o{ tb_monthly_expense_spents : "associa"

    tb_user {
        BIGINT id PK
        VARCHAR nome
        VARCHAR surname
        VARCHAR cpf UNIQUE
        VARCHAR email UNIQUE
        VARCHAR password
    }

    tb_spent {
        BIGINT id PK
        ENUM type
        INT installments
        DOUBLE value
        VARCHAR place
        BIGINT user_id FK
    }

    tb_monthly_expense {
        BIGINT id PK
        INT year
        VARCHAR month
        BIGINT user_id FK
    }

    tb_monthly_expense_spents {
        BIGINT monthly_expense_id PK, FK
        BIGINT spent_id PK, FK
    }
