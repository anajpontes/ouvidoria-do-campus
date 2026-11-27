# [Ouvidoria do Campus]

[English](README.md) · **Português (Brasil)**

Preencham este README e mantenham-no atualizado. Ele faz parte da entrega e deve permanecer na **raiz** do repositório, junto com `DECISIONS.md` e `AI_LOG.md` (e as versões em português: `README.pt-BR.md`, `DECISIONS.pt-BR.md` e `AI_LOG.pt-BR.md`).

A documentação do projeto deve ser escrita **em inglês** (a versão principal é o [`README.md`](README.md)), com uma **versão em português** ao lado. Mantenham as duas versões alinhadas.

Criem, dentro do repositório, as **pastas de cada parte do projeto** que forem desenvolver (por exemplo, `/backend`, `/frontend`, `/docs`). A organização é escolha do time — descrevam-na na seção [Estrutura de pastas](#estrutura-de-pastas).

No primeiro dia, adicionem o professor como colaborador do repositório, com o e-mail **adrianoanschau@gmail.com**.

## Equipe

- **Turma:** TDS — turmas mistas 6570, 6756 e 6863
- **Tech Lead:** Vinicius Esteves
- **Responsável pela apresentação:** Ana Júlia Pontes
- **Integrantes:**
  - Ana Julia — anajpontes
  - Vinicius Esteves — akiyx45
  - Thomas — thomasmchaves-ctrl

## Tema / área

Ouvidoria do campus — vida no campus e comunidade

## Problema

(qual problema vocês estão resolvendo, e para quem)

## Solução

(o que a aplicação faz e qual é o recorte do MVP)

## Stack

- Back-end: Java 17 e Spring Boot.
- Front-end: HTML e CSS.
- Banco de dados: PostgreSQL hospedado no Supabase.

## Estrutura de pastas

Descrevam a organização do repositório. Ajustem o exemplo abaixo à escolha do time.

```text
/
├── README.md
├── README.pt-BR.md
├── DECISIONS.md
├── DECISIONS.pt-BR.md
├── AI_LOG.md
├── AI_LOG.pt-BR.md
├── backend/
├── frontend/
└── docs/
```

## Como rodar

### Pré-requisitos

- Java 17.
- Um projeto Supabase com acesso ao PostgreSQL.
- Dados de conexão disponíveis no painel do Supabase. Não mantenha credenciais no repositório.

### Back-end

```powershell
cd backend
$env:SUPABASE_DB_URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:5432/postgres?sslmode=require"
$env:SUPABASE_DB_USERNAME = "postgres.ccnrgujttgtebecizhij"
$env:SUPABASE_DB_PASSWORD = "<senha-do-banco>"
.\mvnw.cmd spring-boot:run
```

Esses dados correspondem ao **Session Pooler** exibido em **Connect** no projeto Supabase da equipe e atendem a redes IPv4. A URL e o usuário já têm valores padrão em `application.properties`; mantenha as atribuições acima se quiser explicitar a configuração. Informe a senha do banco somente no terminal local. Não a envie pelo chat nem faça commit dela.

Antes de iniciar o backend pela primeira vez, confira no Table Editor se as tabelas já existem. Se estiverem ausentes, execute uma única vez a migration aprovada em [docs/sql/001_create_ouvidoria_tables.sql](docs/sql/001_create_ouvidoria_tables.sql) pelo SQL Editor do Supabase. O Hibernate está em modo `validate` e não cria nem altera tabelas.

### Front-end

```bash
# comandos para instalar dependências e subir o front-end
```

## CI/CD

(descrevam a pipeline de GitHub Actions: o que roda em cada PR e em cada push na `main` — no mínimo build, lint e testes)

## Deploy

(onde a aplicação está publicada, se houver; ou indique que o deploy automático ainda não foi configurado)

## Links

- **Repositório:**
- **Deploy (se houver):**
- **Board / projeto no GitHub (se houver):**
