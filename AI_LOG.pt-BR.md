# Registro de uso de IA (AI_LOG)

[English](AI_LOG.md) · **Português (Brasil)**

Registrem aqui como o time usou inteligência artificial no projeto. O uso de IA é liberado e incentivado, desde que documentado: este arquivo é entregável obrigatório e serve de roteiro para o bloco “IA na prática” da apresentação.

A documentação do projeto deve ser escrita **em inglês** (a versão principal é o [`AI_LOG.md`](AI_LOG.md)), com uma **versão em português** ao lado. Mantenham as duas versões alinhadas.

Mantenham este arquivo na **raiz** do repositório. Incluam as ferramentas utilizadas e, para cada uso relevante, o prompt principal, o que a IA gerou, o que o time aproveitou e o que foi corrigido ou rejeitado — e por quê.

Nunca commitem chaves de API, senhas ou outros segredos. Esses valores vão em `.env` (fora do repositório) e em GitHub Secrets.

Copiem o modelo abaixo sempre que precisarem de uma entrada nova.

## Ferramentas

- GitHub Copilot (Copilot SDK no VS Code).

## Modelo de registro

```markdown
### [data] — [tarefa ou trecho]
- **Prompt principal:**
- **O que a IA gerou:**
- **O que o time aproveitou:**
- **O que o time corrigiu ou rejeitou, e por quê:**
```

## Registro

### 24/11/2026 — Autenticação JWT (exemplo)

- **Prompt principal:** "Gere um middleware de autenticação JWT em Node.js/Express, com tratamento de token expirado e uma rota protegida de exemplo."
- **O que a IA gerou:** um middleware e um exemplo de rota, com uma dependência desatualizada e sem validação de variáveis de ambiente.
- **O que o time aproveitou:** a estrutura do middleware e o fluxo de verificação do token.
- **O que o time corrigiu ou rejeitou, e por quê:** atualizamos a biblioteca, movemos o segredo para variável de ambiente e incluímos testes. O exemplo de rota foi reescrito para se adequar ao nosso modelo de usuário.

### 26/11/2026 — Configuração do PostgreSQL no Supabase

- **Prompt principal:** configurar o projeto para a decisão aprovada pela equipe de usar Supabase.
- **O que a IA gerou:** configuração da conexão PostgreSQL por variáveis de ambiente, alteração das dependências e atualização alinhada da documentação.
- **O que o time aproveitou:** a configuração para PostgreSQL no Supabase e o registro da decisão aprovada.
- **O que o time corrigiu ou rejeitou, e por quê:** não conectar o repositório GitHub porque ele não pertence à pessoa responsável pelo banco; manter as credenciais fora do repositório.

### 27/11/2026 — API pública de relatos e e-mail privado

- **Prompt principal:** Continuar o back-end para receber relatos localmente e depois alinhá-lo ao Supabase do colega sem expor o e-mail do reclamante.
- **O que a IA gerou:** Uma rota JSON de abertura, geração de protocolo UUID, mapeamento JPA alinhado à proposta atual de esquema, criptografia AES-GCM do e-mail com chave de ambiente, além de testes e documentação.
- **O que o time aproveitou:** A rota `POST /api/chamados`, o protocolo UUID, o e-mail obrigatório armazenado separadamente com AES-GCM e uma resposta sem esse dado. O banco da aplicação foi integrado ao PostgreSQL do Supabase; os testes usam H2 isolado.
- **O que o time corrigiu ou rejeitou, e por quê:** A configuração SQLite inicial foi substituída pelo Supabase conforme decisão da equipe. Envio/validação de código, rotas da secretaria, upload de fotos e classificação real por IA continuam adiados porque provedores/políticas ou autenticação ainda não foram aprovados. Credenciais reais não foram adicionadas ao repositório.
