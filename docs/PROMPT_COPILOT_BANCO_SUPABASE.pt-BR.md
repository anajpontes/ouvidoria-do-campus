# Prompt para o Copilot — esquema do banco Supabase

> **Rascunho para revisão da equipe.** Este documento é uma proposta de trabalho, não uma decisão final sobre esquema, segurança ou conexão. Revise com a pessoa responsável pelo banco antes de executar qualquer SQL.

## Como usar

1. Abra o repositório no VS Code.
2. Cole o prompt abaixo no Copilot Chat.
3. Peça para ele inspecionar o código atual antes de gerar a migration.
4. Revise o SQL com a equipe. Execute no Supabase somente depois de aprovação explícita.

## Prompt para copiar

Estamos desenvolvendo o projeto **Ouvidoria do Campus**. Analise o backend existente neste repositório antes de propor alterações. O backend usa Java 17, Spring Boot, Spring Data JPA e possui a classe `br.com.senac.ouvidoria.model.Chamado` e o enum `StatusChamado`. A configuração atual usa SQLite, mas a equipe decidiu migrar o banco para PostgreSQL do Supabase. Ainda precisamos confirmar com a equipe se o Spring conectará por JDBC/JPA ou por outra forma.

Sua tarefa agora é propor o esquema relacional inicial do MVP para revisão. **Não conecte ao Supabase, não execute SQL remoto, não use nem solicite credenciais, não altere `application.properties`, não implemente endpoints e não adicione dependências ou ferramentas de migration.** Não invente regras de autenticação, envio de e-mail ou categorias/prioridades. Se algo impedir um SQL seguro ou compatível, explique a dúvida em vez de assumir.

### Requisitos do produto já informados

- Um relato contém título, descrição e local do campus; a foto é opcional.
- O status começa como `RECEBIDO` e pode ser `EM_ANDAMENTO` ou `RESOLVIDO`.
- A equipe pode registrar uma resposta curta.
- A classificação e a prioridade podem ter sugestão da IA e revisão humana. As categorias e os níveis de prioridade ainda não foram definidos.
- O reclamante pode deixar um e-mail opcional para acompanhamento.
- A secretaria não deve receber o e-mail nos dados da fila.
- O acompanhamento exige um código enviado por e-mail. O ciclo de vida completo do código ainda precisa ser acordado.
- A secretaria precisará autenticar-se. A equipe pretende avaliar Supabase Auth; não crie tabela própria de senhas.
- Fotos deverão ser tratadas pelo Supabase Storage, mas bucket, políticas e limites ainda precisam ser combinados.

### Esquema inicial a avaliar

Avalie estas duas tabelas como proposta mínima. Ajuste somente se encontrar incompatibilidade concreta com o backend ou com PostgreSQL; explique qualquer ajuste.

#### `chamados`

- `id`: UUID, chave primária, gerado com UUID seguro.
- `protocolo`: texto único, não sequencial e gerado com fonte aleatória segura; não deve, sozinho, autorizar consulta.
- `titulo`: texto obrigatório.
- `descricao`: texto obrigatório.
- `localizacao`: texto obrigatório.
- `foto_path`: texto opcional contendo somente a referência do objeto no Storage.
- `status`: texto obrigatório, padrão `RECEBIDO`, restrito a `RECEBIDO`, `EM_ANDAMENTO` e `RESOLVIDO`.
- `tipo_sugerido`, `prioridade_sugerida`: texto opcional.
- `tipo_revisado`, `prioridade_revisada`: texto opcional.
- `resposta_equipe`: texto opcional.
- `created_at`, `updated_at`: timestamps com fuso horário; criação deve usar o horário atual. Explique como `updated_at` será atualizado sem depender de comportamento implícito.

Não crie restrições com listas de categorias ou prioridades: os valores permitidos ainda não foram decididos.

#### `acompanhamento_privado`

- `chamado_id`: UUID, chave primária e chave estrangeira para `chamados.id`.
- `email_cifrado`: texto obrigatório, criptografado pela aplicação antes de ser persistido; a chave de criptografia não pode ficar no banco nem no repositório.
- `codigo_hash`: texto opcional; armazenar somente representação segura do código, nunca o código em texto puro.
- `codigo_expira_em`: timestamp com fuso horário opcional.
- `tentativas`: inteiro pequeno, obrigatório, padrão zero e nunca negativo.
- `confirmado_em`: timestamp com fuso horário opcional.
- `created_at`: timestamp com fuso horário, padrão horário atual.

Se o reclamante não informar e-mail, não deve existir linha correspondente em `acompanhamento_privado`. Para reenvio, considere um único código ativo por chamado: a emissão de um novo código invalida o anterior. Não escolha prazo, limite de tentativas ou política de reenvio sem destacar que são decisões pendentes.

### Segurança e compatibilidade

- O frontend não deve acessar tabelas ou chaves privilegiadas do Supabase diretamente; as chamadas devem passar pela API do backend.
- Não crie políticas RLS permissivas para leitura pública. Explique que uma credencial `service_role` do Supabase ignora RLS e nunca pode ser enviada ao navegador.
- A fila da secretaria não pode receber `email_cifrado`, `codigo_hash` nem outros dados privados de acompanhamento.
- Não crie tabela de usuários/senhas para a secretaria. Não implemente autenticação nesta tarefa.
- Confira a compatibilidade das colunas com JPA e com o enum Java `StatusChamado`. Explique que o enum deve ser persistido como texto, sem assumir ordinal numérico.
- Use nomes consistentes em `snake_case` no SQL e indique explicitamente o mapeamento esperado nas entidades Java.
- Inclua apenas índices necessários para as consultas previstas: unicidade de `protocolo` e índices que ajudem a fila por status e data de criação. Explique a necessidade de qualquer índice adicional.
- Não configure nem crie bucket do Storage sem aprovação da equipe.

### Entrega solicitada

1. Inspecione e relate o estado atual do backend e dependências.
2. Apresente o esquema proposto e suas decisões em linguagem simples.
3. Gere um script SQL PostgreSQL revisável em `docs/sql/001_create_ouvidoria_tables.sql`, sem executá-lo.
4. Explique o mapeamento das colunas para as classes Java e liste as decisões pendentes.
5. Não aplique a migration no Supabase, não altere configurações de conexão e não modifique código Java nesta etapa.

Se o caminho da migration não combinar com os padrões que já existirem no repositório, pare e pergunte antes de criar outra estrutura.
