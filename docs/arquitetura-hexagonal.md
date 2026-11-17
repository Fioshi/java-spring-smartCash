# Arquitetura Hexagonal — SmartCash

O projeto adota a **Arquitetura Hexagonal (*Ports & Adapters*)** nos módulos de negócio `user` e `spent`.

O objetivo central é manter as **regras de negócio e o domínio isolados** de frameworks e detalhes de infraestrutura (Spring, JPA, Web, JWT e iText7), seguindo a regra de dependência de fora para dentro:

```text
adapter/in  ──▶  application/port/in  ◀──  application/usecase  ──▶  domain
                                                   │
                                                   ▼
                                          application/port/out  ◀──  adapter/out
```

---

## 1. Responsabilidade de Cada Camada / Pacote

### Núcleo (`domain` + `application`) — 100% Java Puro
Nenhuma classe dentro de `domain/` ou `application/` possui imports de Spring, Jakarta/JPA, Lombok, JWT ou iText7.

- **`<modulo>.domain.model`**
  - Modelos de domínio e enums puros com as regras intrínsecas da entidade (ex.: `User`, `Spent`, `MonthlyExpense`, `AdditionalIncome`, `SpentCategorie`, `Type`).
  - Exemplo: em `Spent`, o cálculo de parcelas (`value / installments`) e a flag `isMonthly` residem no próprio domínio (`Spent.createNew(...)` e `Spent.update(...)`).

- **`<modulo>.application.port.in`** *(Portas de Entrada / Driving Ports)*
  - Interfaces que expõem os casos de uso do módulo, junto com seus *Commands* e modelos de retorno (ex.: `SignUpUserUseCase`, `InsertSpentUseCase`, `ExportPdfUseCase`, `MonthlySpentSummary`).
  - Os controllers dependem apenas dessas interfaces, chamando cada caso de uso individualmente por endpoint.

- **`<modulo>.application.port.out`** *(Portas de Saída / Driven Ports)*
  - Interfaces que definem o que os casos de uso precisam do mundo externo, sem acoplamento tecnológico:
    - **Persistência:** `UserRepositoryPort`, `SpentRepositoryPort`, `MonthlyExpenseRepositoryPort`, `AdditionalIncomeRepositoryPort`.
    - **Segurança:** `PasswordEncoderPort`, `TokenProviderPort`, `AuthenticationPort`.
    - **Integração entre módulos:** `SpentUserLookupPort` (permite que `spent` consulte usuários via `UserRepositoryPort`, sem acoplar nas entidades JPA de `user`).
    - **Infraestrutura / Saída:** `PdfGeneratorPort`, `TransactionPort`.

- **`<modulo>.application.usecase` (+ `validation/`)**
  - Implementações concretas dos casos de uso (1 classe por caso de uso) e estratégias de validação de negócio (`UserSignUpValidation`, `SpentValidation`).
  - Orquestram domínio e portas de saída sem `@Service` ou `@Transactional` acoplados.

---

### Adaptadores (`adapter/in` e `adapter/out`) — Camada de Frameworks
É nos adaptadores que residem Spring Web, Spring Data JPA, Spring Security, `jjwt` e iText7.

- **`<modulo>.adapter.in.web` (+ `dto/`)**
  - Controllers REST (`UserController`, `AuthenticationController`, `SpentController`, `AdditionalIncomeController`) e DTOs de entrada/saída com validações HTTP (`@Valid`, `@NotBlank`, etc.).
  - Traduzem requisições HTTP para chamadas às portas de entrada (`port/in`).

- **`<modulo>.adapter.out.persistence`**
  - **`entity/`:** entidades JPA (`UserJpaEntity`, `SpentJpaEntity`, `MonthlyExpenseJpaEntity`, `AdditionalIncomeJpaEntity`) com Lombok (`@Getter`, `@NoArgsConstructor`, etc.).
  - **`repository/`:** interfaces `JpaRepository` do Spring Data JPA.
  - **`mapper/`:** conversores bidirecionais entre modelos de domínio e entidades JPA.
  - **`*PersistenceAdapter`:** implementações das portas de saída de repositório.
  - **`SpringTransactionAdapter` / `SpentUserLookupAdapter`:** adaptadores de suporte para transações (`TransactionTemplate`) e consulta de usuário entre módulos.

- **`user.adapter.out.security`**
  - Implementações de `PasswordEncoderPort`, `TokenProviderPort` e `AuthenticationPort` usando Spring Security (`PasswordEncoder`, `AuthenticationManager`) e `JwtUtil`.

- **`spent.adapter.out.pdf`**
  - `ITextPdfGeneratorAdapter`: implementa `PdfGeneratorPort` encapsulando toda a geração de relatórios PDF com iText7.

---

## 2. Composição (*Wiring*) no Spring

Como `application/usecase` não usa `@Service` nem `@Component`, a instanciação e injeção de dependências acontecem na camada de configuração em `infra/config/`:

- `UserBeanConfig`: registra os casos de uso e validações de `user`.
- `SpentBeanConfig`: registra os casos de uso e validações de `spent`.
