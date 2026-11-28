# Decisões do projeto

[English](DECISIONS.md) · **Português (Brasil)**

Registrem aqui as decisões importantes do time. Cada entrada deve ser curta e trazer o que foi decidido, as alternativas consideradas e o motivo da escolha.

A documentação do projeto deve ser escrita **em inglês** (a versão principal é o [`DECISIONS.md`](DECISIONS.md)), com uma **versão em português** ao lado. Mantenham as duas versões alinhadas.

Mantenham este arquivo na **raiz** do repositório. Incluam uma entrada nova para cada decisão relevante (stack, arquitetura, recorte do MVP, o que ficou de fora, caminhos que vocês tentaram e abandonaram etc.). Este arquivo serve de roteiro para o bloco de decisões da apresentação.

Copiem o modelo abaixo sempre que precisarem de uma entrada nova.

## Modelo

```markdown
## [data] — [título da decisão]
- **Decisão:**
- **Alternativas consideradas:**
- **Motivo:**
```

## 23/11/2026 — Escolha da stack de back-end (exemplo)

- **Decisão:** usar Java com Spring no back-end.
- **Alternativas consideradas:** Java com Spring Boot; Python com FastAPI.
- **Motivo:** o time já tem mais familiaridade com JavaScript, e o prazo de uma semana favorece uma stack na qual todos conseguem contribuir desde o primeiro dia.

## [24/11/26] — Escopo do MVP

- **Decisão:** Decidimos criar um sistema onde o usuário podera abrir chamados com texto, foto, e local do campus.
- **Alternativas consideradas:** Consideramos que não podera mandar audios, arquivos (exceto PNG, MP4) e links.
- **Motivo:** O escopo atende aos requisitos mínimos do briefing e pode ser desenvolvido dentro do prazo. Além disso, decidimos mandar somente texto, foto e local do campus, pois é a maneira mais eficaz de relatar um problema.

## 26/11/2026 — Acompanhamento privado por e-mail (decisão inicial)

- **Decisão:** Inicialmente, o reclamante poderia informar um e-mail opcional para receber o protocolo e acompanhar a reclamação. Para consultar pela tela, seria necessário confirmar um código enviado a esse e-mail. O e-mail seria usado somente para acompanhamento e não ficaria visível nem acessível à secretaria. A obrigatoriedade foi alterada em 27/11/2026; ver a decisão abaixo.
- **Alternativas consideradas:** Não oferecer acompanhamento por e-mail ou permitir a consulta sem validar um código enviado ao endereço informado.
- **Motivo:** Permitir o acompanhamento do chamado sem exigir que o reclamante se identifique perante a secretaria. Quem não informar e-mail poderá enviar o relato sem fornecer esse dado pessoal.

## 26/11/2026 — Entrega inicial do frontend

- **Decisão:** Desenvolver primeiro a tela responsiva de abertura de relato em HTML e CSS. Manter o envio desabilitado até existir uma API real; implementar login da secretaria e consulta/validação por código em etapas posteriores.
- **Alternativas consideradas:** Criar todas as telas e simular uma integração antes de o backend disponibilizar os endpoints.
- **Motivo:** Evitar inventar APIs ou apresentar um envio simulado como funcional e manter o desenvolvimento em etapas pequenas.

## 26/11/2026 — Banco de dados com Supabase

- **Decisão:** Usar PostgreSQL hospedado no Supabase, substituindo o SQLite configurado anteriormente. O backend Spring Boot conecta ao Supabase via JPA/JDBC e valida o esquema com `ddl-auto=validate`.
- **Alternativas consideradas:** Manter a configuração SQLite existente.
- **Motivo:** A equipe escolheu Supabase e atribuiu a responsabilidade pelo banco a um colega. Definir primeiro o esquema e o método de conexão evita implementar com base em um contrato presumido.

## 26/11/2026 — Modelo JPA inicial do chamado

- **Decisão:** Preparar a entidade JPA `Chamado` com base nos campos propostos para o relato, mantendo o e-mail fora do modelo de dados visível à secretaria. A configuração SQLite foi considerada inicialmente para testes locais de mapeamento e depois substituída pela integração com Supabase PostgreSQL.
- **Alternativas consideradas:** Aguardar o esquema do banco antes de criar qualquer modelo de domínio ou trocar a configuração de conexão antes de o contrato do Supabase estar pronto.
- **Motivo:** Os campos principais do relato e os status já são conhecidos; seu mapeamento pode ser revisado e testado localmente sem tratar o esquema Supabase ou a API proposta como definitivos.

## 27/11/2026 — Escopo inicial de persistência do back-end

- **Decisão:** A proposta inicial era usar SQLite/JPA até a confirmação do esquema Supabase; essa configuração foi substituída pelo PostgreSQL do Supabase. O e-mail do reclamante é obrigatório e fica em uma entidade privada separada de `Chamado`. Adiar o upload de fotos até que o Supabase Storage esteja pronto. Adiar o envio de códigos e os endpoints de acompanhamento até definir o provedor de e-mail e o ciclo de vida do código; adiar os endpoints da secretaria até integrar o Supabase Auth. Manter os campos de sugestão/revisão da IA, sem alegar classificação real por IA antes da escolha de um provedor.
- **Alternativas consideradas:** Esperar o banco para iniciar todo o back-end, guardar o e-mail junto dos dados visíveis à secretaria, salvar fotos temporariamente no disco local ou publicar rotas sem os serviços de e-mail/autenticação necessários.
- **Motivo:** Permitir o avanço seguro do back-end local sem acoplar o projeto a um esquema não aprovado nem alegar que acompanhamento, fotos, IA ou login da equipe estão completos.

## 27/11/2026 — E-mail obrigatório para abertura de chamado

- **Decisão:** Alterar a decisão inicial: o e-mail passa a ser obrigatório na abertura do chamado e é armazenado separado dos dados visíveis à secretaria, criptografado com AES-GCM.
- **Alternativas consideradas:** Manter o e-mail opcional ou armazená-lo junto aos dados do chamado.
- **Motivo:** Alinhar a API implementada ao requisito atualizado da equipe, protegendo o dado privado.

## 27/11/2026 — Abertura pública de relatos e criptografia do e-mail

- **Decisão:** Implementar `POST /api/chamados` com JSON contendo `title`, `description`, `location` e `email` obrigatório. Retornar protocolo UUID aleatório, status inicial e data de criação, nunca o e-mail. Armazenar o e-mail separado usando AES-GCM com uma chave Base64 de 32 bytes fornecida por `FOLLOW_UP_EMAIL_ENCRYPTION_KEY`; usar Supabase PostgreSQL como banco de dados.
- **Alternativas consideradas:** Manter somente o serviço de persistência sem rota HTTP, armazenar o e-mail em texto puro ou expor uma rota da secretaria antes de haver autenticação.
- **Motivo:** Disponibilizar uma API real e testável para abertura de relatos, protegendo o e-mail privado e mantendo a validação do esquema existente. Envio/consulta de código, rotas da secretaria, fotos e IA real continuam como etapas separadas.
