# Retomada do back-end — Ouvidoria do Campus

Este resumo registra o estado atual do back-end e os próximos passos.

## Estado no Git

- **Branch de trabalho:** `feat/backend-api-supabase`.
- O conteúdo de `feat/backend-supabase-integration` foi integrado nesta branch, mantendo PostgreSQL do Supabase em vez da configuração SQLite trazida pela origem. A branch `feat/supabase-env-config` foi preservada no commit-base.
- Não envie alterações ao GitHub sem uma solicitação explícita; o repositório remoto não pertence à pessoa responsável pelo banco.

## Implementado

- Aplicação Spring Boot com Java 17, Spring Data JPA e conexão JDBC ao PostgreSQL do Supabase.
- `.env` local carregado pelo Spring Boot e ignorado pelo Git; a senha do banco e a chave AES não devem ser commitadas.
- Entidades JPA para `chamados` e `acompanhamento_privado`; Hibernate usa `ddl-auto=validate`.
- `POST /api/chamados` aceita JSON (`title`, `description`, `location`, `email`), exige e-mail, gera protocolo UUID e retorna `protocol`, `status` e `createdAt`, sem incluir o e-mail na resposta.
- O e-mail obrigatório fica separado dos dados do chamado e é cifrado com AES-GCM. `FOLLOW_UP_EMAIL_ENCRYPTION_KEY` deve ser Base64 e decodificar para 32 bytes; preserve a mesma chave para ler dados existentes.
- Os testes usam H2 em memória para não escrever no Supabase compartilhado.
- README, contrato, decisões e registro de uso de IA descrevem a integração atual.

## Executar localmente

Na pasta `backend`, copie `.env.example` para `.env` e preencha `SUPABASE_DB_PASSWORD` e `FOLLOW_UP_EMAIL_ENCRYPTION_KEY`. A chave AES deve ser Base64 de 32 bytes. Não compartilhe nem versione o arquivo.

```powershell
.\mvnw.cmd spring-boot:run
```

Para executar os testes, também na pasta `backend`:

```powershell
.\mvnw.cmd test
```

Os testes utilizam banco H2 isolado e não requerem a senha do Supabase.

## Ainda não implementado / pendente de decisão

- Envio e validação de código por e-mail, consulta pública de acompanhamento e regras de expiração/tentativas/reenvio.
- Rotas autenticadas da secretaria via Supabase Auth.
- Upload de fotos via Supabase Storage e classificação real por IA.
- Alinhamento final do formato do protocolo e contrato JSON com o frontend.
- Política de armazenamento, rotação e recuperação da chave de criptografia para produção.

O e-mail do reclamante não deve aparecer em respostas nem ser acessível à secretaria. Não commitar chaves, credenciais ou tokens.
