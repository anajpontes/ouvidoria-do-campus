# Contrato do back-end e Supabase — rascunho

> **Status: proposta para revisão.** Este contrato ainda não foi aprovado pela equipe e não deve ser tratado como API implementada nem como esquema final do banco.

## Contexto

O back-end usa Spring Boot com Spring Data JPA e está configurado atualmente para SQLite. A equipe decidiu usar Supabase como banco, e um colega está desenvolvendo essa parte. Ainda não existem endpoints de API. Este rascunho serve para alinhar o trabalho do back-end e do banco antes da implementação.

## Requisitos já acordados

- O relato tem título, descrição e local no campus; a foto é opcional, mas o upload real será adiado até o Storage estar pronto.
- O reclamante deve informar um e-mail para receber o código de validação e acompanhar o relato.
- O e-mail não pode ficar visível nem acessível à secretaria.
- Para consultar o relato pela tela pública, é necessário validar um código enviado por e-mail.
- A secretaria precisa fazer login para acessar a fila.
- A equipe pode filtrar por status, tipo e prioridade, atualizar um relato e adicionar uma resposta curta.
- Os status são `RECEBIDO`, `EM_ANDAMENTO` e `RESOLVIDO`.
- A classificação e a prioridade da IA são sugestões e precisam de revisão humana.

## Limites de implementação acordados

- Continuar o desenvolvimento local com SQLite/JPA enquanto o responsável pelo banco finaliza o esquema do Supabase. A integração planejada é com o PostgreSQL do Supabase via JPA depois que o esquema for confirmado.
- Armazenar o e-mail obrigatório separado dos dados de `chamados` usados pela secretaria e persistir os dois registros atomicamente. Esta etapa de persistência não implementa abertura do relato, envio do código, acompanhamento público nem endpoints da fila da secretaria.
- Adiar o upload de fotos até que o Supabase Storage e sua política de acesso sejam definidos. Não salvar os arquivos no disco local como substituto temporário.
- Adiar endpoints da secretaria até integrar o Supabase Auth. Manter os campos de sugestão/revisão, mas adiar a classificação real por IA até que um provedor seja escolhido.

## Integração proposta

A direção acordada é conectar o Spring Boot ao PostgreSQL do Supabase usando JDBC/JPA e reaproveitar a dependência Spring Data JPA existente, depois que o responsável pelo banco confirmar o esquema. O SQLite continuará como banco de desenvolvimento local até lá.

As credenciais devem ser fornecidas por variáveis de ambiente ou outro gerenciador de segredos aprovado. Não commitem credenciais nem as colem no código-fonte ou em conversas compartilhadas.

## Separação de dados proposta

Estas são sugestões, não um esquema final:

- `chamados`: conteúdo do relato, local, status, tipo/prioridade revisados e resposta da equipe. Não incluir o e-mail do reclamante nos campos retornados à secretaria.
- Dados privados de acompanhamento: armazenar o e-mail obrigatório separado dos dados do relato usados pela secretaria, com acesso restrito ao fluxo de acompanhamento do back-end. Nesta etapa não há código de validação; quando implementado, não o armazenar em texto puro.
- Foto: o upload está adiado; não salvar arquivos no disco local. Quando o Supabase Storage estiver pronto, guardar no registro do chamado apenas a referência ao objeto e confirmar com o responsável pelo banco as permissões do bucket e os limites de upload.
- Nomes das tabelas e campos, tipos, identificadores, restrições e representação da revisão humana precisam ser acordados com o responsável pelo banco.

## Formato de API proposto

Os nomes de rotas e os dados abaixo são candidatos para discussão, não endpoints aprovados.

### Abertura pública de relato

`POST /api/chamados`

Entradas candidatas: título, descrição, local no campus, e-mail obrigatório para acompanhamento e foto opcional quando o Storage estiver integrado. O formato da requisição (JSON mais envio de foto separado ou `multipart/form-data`) depende da estratégia aprovada para armazenar fotos. Este endpoint não faz parte da etapa atual, limitada à persistência, porque o envio do código ainda não está configurado.

A resposta pode incluir um protocolo não secreto. O protocolo sozinho não deve permitir acesso ao relato; a consulta também deve validar o código enviado por e-mail.

### Consulta pública do relato

O fluxo do frontend tem etapas separadas para solicitar e validar um código. Rotas candidatas:

- `POST /api/acompanhamento/codigo` com o protocolo para enviar um código ao e-mail armazenado.
- `POST /api/acompanhamento/consultar` com o protocolo e o código de validação.

Retornar somente os dados necessários ao autor; nunca retornar o e-mail privado. Os nomes das rotas, a expiração, as tentativas, o reenvio e o provedor de e-mail ainda precisam ser confirmados. Essas rotas estão adiadas.

### Fila e atualização pela secretaria

- `GET /api/secretaria/chamados` com filtros opcionais por status, tipo e prioridade.
- `PATCH /api/secretaria/chamados/{id}` para atualizar tipo/prioridade revisados, status e resposta curta.

As duas rotas devem exigir autenticação da secretaria. O método de autenticação e a criação de usuários/perfis ainda não foram definidos.

## Decisões necessárias antes da implementação

1. Contrato das tabelas e colunas, incluindo identificadores e valores de status, tipo e prioridade.
2. Armazenamento de fotos: política do bucket do Supabase Storage e limites de upload.
3. Provedor de e-mail e ciclo de vida do código de validação (expiração, tentativas e reenvio), além dos controles que impedem o acesso da secretaria aos dados privados de acompanhamento.
4. Provisionamento e regras de acesso da secretaria usando Supabase Auth.
5. Representação dos valores de tipo/prioridade sugeridos pela IA e revisados por uma pessoa, e provedor de IA.

Depois de confirmar estes pontos, atualizem o rascunho e implementem uma etapa do back-end por vez. Não criem as rotas ou o esquema propostos como se já estivessem aprovados.
