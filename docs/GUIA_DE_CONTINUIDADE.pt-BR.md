# Guia de continuidade — Ouvidoria do Campus

Este guia ajuda a equipe a continuar o desenvolvimento mantendo o mesmo escopo, tecnologias e forma de trabalho. Consulte também o contexto completo do projeto e os arquivos `DECISIONS.md` / `DECISIONS.pt-BR.md`.

## Objetivo e escopo

O sistema recebe relatos de problemas de infraestrutura do campus e permite que a equipe acompanhe e organize os chamados. O MVP deve priorizar:

1. abertura e armazenamento de relatos;
2. listagem e organização da fila da secretaria;
3. filtros por status, tipo e prioridade;
4. atualização de status e resposta curta;
5. acompanhamento pelo reclamante;
6. sugestões de classificação/prioridade da IA, sempre revisadas por uma pessoa.

Status do chamado: `recebido`, `em andamento` e `resolvido`.

Não acrescentem funcionalidades fora do MVP sem decisão explícita da equipe.

## Tecnologias e estrutura

- **Back-end:** Java com Spring Boot.
- **Front-end:** HTML, CSS e JavaScript sem frameworks.
- **Banco:** PostgreSQL hospedado no Supabase.
- O front-end deve consumir a API real do back-end. Modos de demonstração podem usar dados simulados se estiverem claramente identificados como fictícios; não inventem endpoints nem apresentem dados simulados como reais.
- Não adicionem frameworks ou dependências sem aprovação da equipe.

O back-end Spring Boot já disponibiliza `POST /api/chamados`, que recebe `title`, `description`, `location` e `email`, e retorna protocolo, status inicial e data de criação sem expor o e-mail. O e-mail é armazenado separadamente e criptografado com AES-GCM. A conexão PostgreSQL usa as variáveis de ambiente `SUPABASE_DB_URL`, `SUPABASE_DB_USERNAME` e `SUPABASE_DB_PASSWORD`; não versionem credenciais. O front-end ainda usa `frontend/mock-api.js`; confirmem o contrato, o tratamento de erros e a configuração de CORS antes de substituir o mock pela API real.

## Regras decididas para os relatos

- O reclamante informa título, descrição e local; a foto é opcional.
- O reclamante informa um e-mail obrigatório para receber o protocolo e acompanhar a reclamação.
- A consulta pela tela exige confirmar um código enviado para esse e-mail.
- O e-mail serve somente para acompanhamento e não pode ficar visível nem acessível à secretaria.
- A equipe da secretaria precisa de login para acessar a fila.
- A classificação e a prioridade sugeridas pela IA precisam de revisão humana.

As regras acima não significam que todo o MVP esteja implementado. A abertura pela API real e o armazenamento privado do e-mail estão implementados; consulta por código, login da equipe, fila e IA real ainda não estão integrados ao back-end.

Não inventem autenticação, formato/expiração/reenvio de código, armazenamento de fotos ou regras de classificação e prioridade para o sistema real. O mock demonstra somente o fluxo visual: não envia e-mails ou fotos, não autentica a equipe e não usa IA real. Se forem necessárias regras reais ainda não decididas, perguntem à equipe antes de implementar.

## Estado atual do front-end

A demonstração das telas está em:

- `frontend/index.html`
- `frontend/consulta.html`
- `frontend/equipe-login.html`
- `frontend/fila.html`
- `frontend/styles.css`
- `frontend/mock-api.js`
- `frontend/app.js`
- `frontend/assets/senac-logo-orange-blue.png`

O protótipo permite criar um relato, consultar por protocolo e código fictício e revisar registros na fila. A fila exige um login de demonstração (`secretaria@demo.local` / `campus123`), mantido somente no `sessionStorage`; isso não é autenticação real e não protege dados. Os relatos ficam somente no `sessionStorage` do navegador; o código não é enviado por e-mail; fotos não são enviadas; as sugestões são fictícias e não são produzidas por IA. A integração do front-end com a API real ainda não foi implementada.

Para visualizar a tela, execute na raiz do repositório:

```powershell
python -m http.server 4173 --directory frontend
```

Depois abra <http://localhost:4173>. Encerre o servidor com `Ctrl+C`.

## Processo para cada etapa

1. Leia este guia, o contexto original e o código relacionado antes de alterar arquivos.
2. Faça uma etapa pequena e delimitada; não implemente várias fases do MVP de uma vez.
3. Antes de editar, diga o objetivo e quais arquivos serão afetados.
4. Reaproveite a estrutura existente e não altere arquivos fora do escopo.
5. Se faltar uma decisão de produto, API ou segurança, pare e pergunte em vez de assumir.
6. Teste o comportamento específico alterado e confira a versão para celular.
7. Ao concluir, informe o que mudou, arquivos, como testar, resultado e pendências.

## Registro de decisões e uso de IA

Toda decisão relevante deve ser registrada nas duas versões, na mesma etapa:

- `DECISIONS.md`: escrever em inglês.
- `DECISIONS.pt-BR.md`: escrever em português.

Mantenha as entradas equivalentes: mesma data, decisão, alternativas consideradas e motivo. Registre decisões reais da equipe; não apresente uma proposta como se já tivesse sido aprovada. Se uma alteração misturar mudanças locais de outra pessoa, preserve-as e não as inclua no commit sem autorização.

Registre usos relevantes de IA também nos arquivos `AI_LOG.md` e `AI_LOG.pt-BR.md`, mantendo as versões alinhadas.

## Branches, commits e pull requests

Trabalhe em uma branch por tarefa. Exemplos:

```text
feat/nome-da-tarefa
fix/nome-do-problema
docs/nome-da-documentacao
```

Use Conventional Commits e escreva as mensagens de commit em inglês:

```text
feat(frontend): add complaint report screen
fix(frontend): correct report form layout on mobile
docs: add development handoff guide
```

Formato: `type(optional-scope): imperative summary`. Use tipos como `feat`, `fix`, `docs`, `test` e `refactor`. Antes de commitar, confira `git status` e `git diff --cached` para garantir que somente arquivos da tarefa serão incluídos.

Envie alterações por Pull Request para revisão; não faça push, merge ou alterações no GitHub sem autorização explícita.

## Verificações antes de concluir

- Abra a página e confira a navegação, textos e validações nativas.
- Teste larguras de celular, tablet e desktop.
- Confirme que não há botões que aparentem funcionar sem ter ação implementada.
- Para integrações, teste sucesso, carregamento e erros da API real.
- Não marque como concluído o que ainda depende de back-end ou de uma decisão pendente.
