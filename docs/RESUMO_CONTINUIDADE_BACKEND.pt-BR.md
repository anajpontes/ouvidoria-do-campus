# Retomada do back-end — Ouvidoria do Campus

Este resumo registra o estado do back-end e como continuar o trabalho em outro computador.

## Estado no Git

- **Branch ativa:** `feat/backend-supabase-integration`
- **HEAD:** `bd3ccba` — `feat(backend): persist private complainant follow-up data`
- A branch local está um commit à frente de `origin/feat/backend-supabase-integration`.
- A implementação da rota de abertura, criptografia e documentação descritas abaixo ainda está no diretório de trabalho e não foi commitada.
- Antes de trocar de computador, revise `git status`; será necessário commitar e enviar as alterações locais para que estejam disponíveis no clone remoto.

No outro computador, depois que as alterações forem enviadas:

```powershell
git fetch origin
git switch --track origin/feat/backend-supabase-integration
```

Se a branch local já existir:

```powershell
git switch feat/backend-supabase-integration
git pull
```

## Implementado

- Aplicação Spring Boot com Java 17, Spring Data JPA e SQLite para desenvolvimento local.
- Entidade `Chamado` com UUID, protocolo, título, descrição, localização, status, classificação/prioridade sugeridas e revisadas, resposta da equipe e timestamps.
- Status persistidos como texto: `RECEBIDO`, `EM_ANDAMENTO`, `RESOLVIDO`.
- E-mail obrigatório armazenado em uma entidade privada separada de `Chamado`, dentro da mesma transação.
- `POST /api/chamados` aceita JSON (`title`, `description`, `location`, `email`), cria protocolo UUID aleatório e retorna `protocol`, `status` e `createdAt`. A resposta não contém o e-mail.
- O e-mail é cifrado com AES-GCM usando `FOLLOW_UP_EMAIL_ENCRYPTION_KEY`, uma chave Base64 de 32 bytes. A chave não pode ser commitada; mantenha a mesma chave para acessar dados já cifrados.
- O mapeamento da entidade privada acompanha provisoriamente a proposta do colega (`acompanhamento_privado`, `chamado_id`, `email_cifrado`). O esquema ainda não foi aprovado nem aplicado.
- As versões em inglês e português do contrato e das decisões foram atualizadas. O uso de IA também foi registrado em `AI_LOG.md` e `AI_LOG.pt-BR.md`.

## Executar localmente

No PowerShell, a partir da pasta `backend`, gere uma chave para a execução e inicie a aplicação:

```powershell
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
$env:FOLLOW_UP_EMAIL_ENCRYPTION_KEY = [Convert]::ToBase64String($bytes)
$rng.Dispose()
.\mvnw.cmd spring-boot:run
```

Para executar os testes, na pasta `backend`:

```powershell
.\mvnw.cmd test
```

Se já houver dados cifrados que precisem ser lidos, não gere uma chave nova: recupere a chave anterior do gerenciador seguro. A chave não está no repositório.

## Ainda não implementado / não aprovado

- Envio e validação de código por e-mail, consulta pública de acompanhamento e suas regras de expiração/tentativas/reenvio.
- Rotas autenticadas da secretaria via Supabase Auth.
- Upload de fotos via Supabase Storage e classificação real por IA.
- Conexão real ao PostgreSQL do Supabase. O SQL do colega é proposta; não executar migration nem presumir que o mapeamento JPA está aprovado.
- Política de armazenamento, rotação e recuperação da chave de criptografia para produção.
- Alinhamento final do formato do protocolo e contrato JSON com o frontend.

## Próximos passos

1. Revisar a proposta do esquema com o responsável pelo banco e só então alinhar as entidades JPA e a configuração PostgreSQL.
2. Alinhar `POST /api/chamados` e os campos de resposta com o colega do frontend.
3. Decidir provedor de e-mail e política do código antes de desenvolver o fluxo de acompanhamento.
4. Integrar Supabase Auth antes de criar rotas da secretaria; definir Storage e IA em etapas próprias.
5. Antes da integração PostgreSQL, validar o esquema aprovado e evitar `ddl-auto=update` em produção.

O e-mail do reclamante não deve aparecer em respostas nem ser acessível à secretaria. Não commitar chaves, credenciais ou tokens.
