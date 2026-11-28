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
- Front-end: HTML, CSS e JavaScript sem frameworks.
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

Copie `backend/.env.example` para `backend/.env` e preencha `SUPABASE_DB_PASSWORD` e
`FOLLOW_UP_EMAIL_ENCRYPTION_KEY` no arquivo local. A chave de criptografia deve estar
em Base64 e decodificar para 32 bytes. Mantenha a mesma chave para acessar e-mails já
cifrados. O `.env` é ignorado pelo Git; não o faça commit nem o compartilhe.

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

O Spring Boot carrega o `.env` local ao iniciar dentro da pasta `backend`. A URL e o usuário do **Session Pooler** do Supabase já têm valores padrão em `application.properties`; use variáveis de ambiente para substituí-los somente se os dados de conexão forem diferentes.
A API aceita requisições de `http://localhost:4173` por padrão. Defina `FRONTEND_ORIGIN` se o front-end for servido de outra origem.

Antes de iniciar o backend pela primeira vez, confira no Table Editor se as tabelas já existem. Se estiverem ausentes, execute uma única vez a migration aprovada em [docs/sql/001_create_ouvidoria_tables.sql](docs/sql/001_create_ouvidoria_tables.sql) pelo SQL Editor do Supabase. O Hibernate está em modo `validate` e não cria nem altera tabelas.

### Front-end

Na raiz do repositório, execute:

```powershell
python -m http.server 4173 --directory frontend
```

Abra <http://localhost:4173>. A URL base da API está na meta tag `api-base-url` das páginas do front-end. Os relatos são enviados ao backend real, e o reclamante pode consultar o status e a resposta da equipe usando o protocolo e o código compartilhado de demonstração `xy1234XY`. Esse código é somente para o trabalho escolar e não deve proteger dados sensíveis ou de produção. O envio de e-mail e as ferramentas da equipe continuam indisponíveis.

## CI/CD

(descrevam a pipeline de GitHub Actions: o que roda em cada PR e em cada push na `main` — no mínimo build, lint e testes)

## Deploy

(onde a aplicação está publicada, se houver; ou indique que o deploy automático ainda não foi configurado)

## Links

- **Repositório:**
- **Deploy (se houver):**
- **Board / projeto no GitHub (se houver):**
