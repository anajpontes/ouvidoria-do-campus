# Retomada do back-end — Ouvidoria do Campus

Este resumo registra o ponto atual do trabalho para continuar em outro computador.

## Estado no Git

- **Branch:** `feat/backend-supabase-integration`
- **Commit enviado ao GitHub:** `c64f362` — `feat(backend): add initial report model and Supabase schema draft`
- **Remoto:** `origin/feat/backend-supabase-integration`
- As alterações desta tarefa estão commitadas e enviadas. Um arquivo local `.vscode/settings.json` ficou fora do commit e não é necessário para continuar.

## Como continuar em casa

No repositório clonado no notebook:

```bash
git fetch origin
git switch --track origin/feat/backend-supabase-integration
```

Se a branch já existir localmente:

```bash
git switch feat/backend-supabase-integration
git pull
```

No Windows, para rodar os testes:

```powershell
cd backend
.\mvnw.cmd test
```

## O que já foi feito

- Branch própria para o trabalho de back-end.
- Esqueleto Spring Boot com Java 17, Spring Data JPA e configuração SQLite existente.
- Entidade inicial `Chamado` em `backend/src/main/java/br/com/senac/ouvidoria/model/Chamado.java`, com UUID, protocolo, título, descrição, localização, caminho opcional da foto, status, classificação/prioridade sugeridas e revisadas, resposta da equipe e timestamps.
- Status persistido como texto: `RECEBIDO`, `EM_ANDAMENTO`, `RESOLVIDO`.
- O e-mail do reclamante não está na entidade `Chamado`; o modelo privado de acompanhamento ainda não foi implementado.
- Teste de persistência local adicionado em `backend/src/test/java/br/com/senac/ouvidoria/OuvidoriaApplicationTests.java`.
- **Validação concluída:** `.\mvnw.cmd test` passou com 2 testes.
- Decisões registradas em `DECISIONS.md` e `DECISIONS.pt-BR.md`.

## Supabase: estado e documentos

A equipe decidiu usar Supabase. Um colega está responsável pelo banco. Ainda precisam ser confirmados:

1. Esquema final das tabelas e nomes/tipos das colunas.
2. Método de conexão do Spring: PostgreSQL por JDBC/JPA ou outra opção.
3. Armazenamento e permissões das fotos no Supabase Storage.
4. Proteção do e-mail, emissão/expiração/reenvio do código e limites de tentativas.
5. Autenticação e permissões da secretaria.
6. Valores válidos de tipo e prioridade.

Documentos para compartilhar/revisar com o colega:

- `docs/BACKEND_SUPABASE_CONTRACT_DRAFT.pt-BR.md` — proposta de contrato em português.
- `docs/BACKEND_SUPABASE_CONTRACT_DRAFT.md` — proposta de contrato em inglês.
- `docs/PROMPT_COPILOT_BANCO_SUPABASE.pt-BR.md` — instruções para o colega usar no Copilot ao preparar o SQL.

**Os documentos são propostas, não um esquema aprovado.** Nenhuma migration foi aplicada e nenhuma conexão real com o Supabase foi configurada. O teste atual usa SQLite local.

## Próximos passos recomendados

1. Pedir ao colega responsável pelo banco para revisar o prompt e gerar/propor o SQL.
2. Revisar o SQL com a equipe e confirmar método de conexão e decisões de segurança antes de executá-lo.
3. Atualizar este contrato e as duas versões de `DECISIONS` quando as decisões forem aprovadas.
4. Só então configurar o acesso ao Supabase usando variáveis de ambiente/segredos fora do Git.
5. Ajustar o mapeamento JPA ao esquema aprovado e testar a persistência contra o banco definido.
6. Definir o contrato da API com quem está trabalhando no frontend; depois implementar endpoints reais, um fluxo por etapa.

Não criar endpoints fictícios, não enviar chaves privilegiadas ao navegador e não commitar credenciais. A secretaria não deve receber o e-mail nos dados retornados pela API.
