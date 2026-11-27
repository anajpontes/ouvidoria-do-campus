# Retomada do back-end — Ouvidoria do Campus

Este resumo registra o ponto atual do trabalho para continuar em outro computador.

## Estado no Git

- **Branch:** `feat/backend-supabase-integration`
- **HEAD atual:** `1d8d64e` — `docs: add backend continuation summary`
- **Remoto:** `origin/feat/backend-supabase-integration`
- A branch local estava sincronizada com o remoto antes desta etapa de implementação.
- As alterações recentes de persistência e documentação estão **no diretório de trabalho e ainda não foram commitadas**. Revise o `git status` antes de criar um commit.

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
- Modelo `AcompanhamentoPrivado` criado para guardar o e-mail obrigatório separado de `Chamado`, com vínculo único por chamado e validação de e-mail.
- Repositórios JPA de chamado e acompanhamento privado criados.
- `RegistrarChamadoService` salva o chamado e o e-mail privado na mesma transação e rejeita e-mail inválido e chamados já persistidos.
- **Validação concluída:** `.\mvnw.cmd test` passou com 4 testes.
- As dependências existentes no `backend/pom.xml` já atendem a esta etapa; o arquivo não precisou ser alterado.
- Decisões e contratos atualizados em `DECISIONS.md`, `DECISIONS.pt-BR.md` e nas duas versões de `docs/BACKEND_SUPABASE_CONTRACT_DRAFT`.

## Supabase: estado e documentos

A equipe decidiu usar Supabase e um colega está responsável pelo esquema. A direção acordada é integrar o Spring Boot ao PostgreSQL do Supabase via JPA depois que o esquema for confirmado. SQLite continua sendo usado localmente enquanto isso. Ainda precisam ser confirmados:

1. Esquema final das tabelas e nomes/tipos das colunas, incluindo o alinhamento do modelo privado com o esquema do colega.
2. Provedor de e-mail e política do código de acompanhamento (expiração, tentativas e reenvio), mantendo o e-mail inacessível à secretaria.
3. Provisionamento e permissões da secretaria usando Supabase Auth.
4. Armazenamento e permissões das fotos no Supabase Storage.
5. Valores oficiais de tipo/prioridade e provedor para classificação real por IA.

Documentos para compartilhar/revisar com o colega:

- `docs/BACKEND_SUPABASE_CONTRACT_DRAFT.pt-BR.md` — proposta de contrato em português.
- `docs/BACKEND_SUPABASE_CONTRACT_DRAFT.md` — proposta de contrato em inglês.
- `docs/PROMPT_COPILOT_BANCO_SUPABASE.pt-BR.md` — instruções para o colega usar no Copilot ao preparar o SQL.

**Os documentos são propostas, não um esquema aprovado.** Nenhuma migration foi aplicada e nenhuma conexão real com o Supabase foi configurada. O teste atual usa SQLite local.

## Próximos passos recomendados

1. Alinhar com o colega do banco o esquema privado do e-mail e o restante das tabelas; não aplicar migration presumida.
2. Definir o provedor de envio e o ciclo de vida do código, além do formato do protocolo e do contrato da API com o colega do frontend.
3. Implementar e testar a submissão pública e o fluxo de solicitar/validar código; esses endpoints ainda não existem.
4. Quando o esquema do Supabase estiver pronto, configurar a conexão PostgreSQL/JPA com segredos fora do Git e validar o mapeamento contra esse banco.
5. Integrar Supabase Auth antes de criar endpoints protegidos para a fila e atualização da secretaria.
6. Integrar fotos via Supabase Storage e decidir o provedor/valores oficiais da classificação por IA.

Não criar endpoints fictícios, não enviar chaves privilegiadas ao navegador e não commitar credenciais. A secretaria não deve receber o e-mail nos dados retornados pela API.
