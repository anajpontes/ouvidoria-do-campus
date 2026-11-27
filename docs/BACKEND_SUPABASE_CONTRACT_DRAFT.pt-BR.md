# Contrato do back-end e Supabase — rascunho

> **Status: proposta para revisão.** Este contrato ainda não foi aprovado pela equipe e não deve ser tratado como API implementada nem como esquema final do banco.

## Contexto

O back-end usa Spring Boot com Spring Data JPA e está configurado atualmente para SQLite. A equipe decidiu usar Supabase como banco, e um colega está desenvolvendo essa parte. A única rota HTTP implementada é a abertura local de relato; acompanhamento e rotas da secretaria ainda não foram implementados. Este rascunho alinha o trabalho enquanto o esquema final e as demais decisões da API estão pendentes.

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
- `POST /api/chamados` recebe JSON com o relato e o e-mail obrigatório, gera um protocolo UUID aleatório e retorna protocolo, status inicial e data de criação. Não envia e-mail nem inclui o endereço na resposta.
- Armazenar o e-mail obrigatório separado dos dados de `chamados` usados pela secretaria e persistir os dois registros atomicamente. Cifrar o e-mail com AES-GCM usando uma chave de 32 bytes fornecida por `FOLLOW_UP_EMAIL_ENCRYPTION_KEY`; nunca commitar a chave. A política de rotação ainda precisa ser definida para produção.
- O mapeamento JPA atual segue a tabela privada proposta na branch do banco (`acompanhamento_privado`, `chamado_id`, `email_cifrado`), mas continua provisório até o responsável pelo banco aprovar o esquema.
- O envio do código, o acompanhamento público e os endpoints da secretaria ainda não foram implementados.
- Adiar o upload de fotos até que o Supabase Storage e sua política de acesso sejam definidos. Não salvar os arquivos no disco local como substituto temporário.
- Adiar endpoints da secretaria até integrar o Supabase Auth. Manter os campos de sugestão/revisão, mas adiar a classificação real por IA até que um provedor seja escolhido.

## Integração proposta

A direção acordada é conectar o Spring Boot ao PostgreSQL do Supabase usando JDBC/JPA e reaproveitar a dependência Spring Data JPA existente, depois que o responsável pelo banco confirmar o esquema. O SQLite continuará como banco de desenvolvimento local até lá.

As credenciais devem ser fornecidas por variáveis de ambiente ou outro gerenciador de segredos aprovado. Não commitem credenciais nem as colem no código-fonte ou em conversas compartilhadas.

Para executar localmente, gere uma chave aleatória de 32 bytes em Base64 e mantenha-a na variável de ambiente durante a execução:

```powershell
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
$env:FOLLOW_UP_EMAIL_ENCRYPTION_KEY = [Convert]::ToBase64String($bytes)
$rng.Dispose()
.\mvnw.cmd spring-boot:run
```

Mantenha a mesma chave para conseguir descriptografar os e-mails já armazenados. Não a salve no repositório.

## Separação de dados proposta

O SQL e o mapeamento JPA são propostas, não um esquema final:

- `chamados`: conteúdo do relato, local, status, tipo/prioridade revisados e resposta da equipe. Não incluir o e-mail do reclamante nos campos retornados à secretaria.
- `acompanhamento_privado`: armazenar o e-mail cifrado pela aplicação, separado dos dados usados pela secretaria. A API atual não expõe esse registro. Códigos futuros devem ser armazenados como hash seguro, nunca em texto puro.
- Foto: o upload está adiado; não salvar arquivos no disco local. Quando o Supabase Storage estiver pronto, guardar no registro do chamado apenas a referência ao objeto e confirmar com o responsável pelo banco as permissões do bucket e os limites de upload.
- Nomes das tabelas e campos, tipos, identificadores, restrições e representação da revisão humana precisam ser acordados com o responsável pelo banco.

## Formato da API

A rota local implementada ainda precisa ser revisada com o frontend e com o esquema. As demais rotas abaixo são candidatas, não aprovadas nem implementadas.

### Abertura pública de relato

`POST /api/chamados`

Entradas JSON implementadas: `title`, `description`, `location` e `email` obrigatório. O envio de foto não é suportado. A resposta `201 Created` contém `protocol`, `status` e `createdAt`; nunca inclui o e-mail. O protocolo é um UUID aleatório e não basta para acessar um relato.

Esta rota não envia código. A futura consulta deve exigir um código enviado ao e-mail armazenado antes de retornar os dados do relato.

### Consulta pública do relato

O fluxo do frontend tem etapas separadas para solicitar e validar um código. Rotas candidatas:

- `POST /api/acompanhamento/codigo` com o protocolo para enviar um código ao e-mail armazenado.
- `POST /api/acompanhamento/consultar` com o protocolo e o código de validação.

Retornar somente os dados necessários ao autor; nunca retornar o e-mail privado. Os nomes das rotas, a expiração, as tentativas, o reenvio e o provedor de e-mail ainda precisam ser confirmados. Essas rotas estão adiadas.

### Fila e atualização pela secretaria

- `GET /api/secretaria/chamados` com filtros opcionais por status, tipo e prioridade.
- `PATCH /api/secretaria/chamados/{id}` para atualizar tipo/prioridade revisados, status e resposta curta.

As duas rotas devem exigir autenticação da secretaria. O método de autenticação e a criação de usuários/perfis ainda não foram definidos.

## Decisões necessárias para as próximas etapas

1. Contrato das tabelas e colunas, incluindo identificadores e valores de status, tipo e prioridade.
2. Armazenamento de fotos: política do bucket do Supabase Storage e limites de upload.
3. Provedor de e-mail e ciclo de vida do código de validação (expiração, tentativas e reenvio), além dos controles que impedem o acesso da secretaria aos dados privados de acompanhamento.
4. Armazenamento, rotação e recuperação da chave de criptografia dos e-mails já armazenados.
5. Provisionamento e regras de acesso da secretaria usando Supabase Auth.
6. Representação dos valores de tipo/prioridade sugeridos pela IA e revisados por uma pessoa, e provedor de IA.

Depois de confirmar estes pontos, atualizem o rascunho e implementem uma etapa do back-end por vez. Não criem as rotas ou o esquema propostos como se já estivessem aprovados.
