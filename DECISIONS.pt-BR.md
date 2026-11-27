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

## 26/11/2026 — Acompanhamento privado por e-mail

- **Decisão:** O reclamante pode informar um e-mail opcional para receber o protocolo e acompanhar a reclamação. Para consultar a reclamação pela tela, será necessário confirmar um código enviado para esse e-mail. O e-mail será usado somente para acompanhamento e não ficará visível nem acessível à secretaria.
- **Alternativas consideradas:** Não oferecer acompanhamento por e-mail ou permitir a consulta sem validar um código enviado ao endereço informado.
- **Motivo:** Permitir o acompanhamento do chamado sem exigir que o reclamante se identifique perante a secretaria. Quem não informar e-mail poderá enviar o relato sem fornecer esse dado pessoal.

## 26/11/2026 — Entrega inicial do frontend

- **Decisão:** Desenvolver primeiro a tela responsiva de abertura de relato em HTML e CSS. Manter o envio desabilitado até existir uma API real; implementar login da secretaria e consulta/validação por código em etapas posteriores.
- **Alternativas consideradas:** Criar todas as telas e simular uma integração antes de o backend disponibilizar os endpoints.
- **Motivo:** Evitar inventar APIs ou apresentar um envio simulado como funcional e manter o desenvolvimento em etapas pequenas.

## 26/11/2026 — Banco de dados com Supabase

- **Decisão:** Usar o Supabase como banco de dados do projeto, substituindo o SQLite configurado anteriormente. O colega responsável pelo banco definirá o esquema; a integração com o Spring Boot seguirá o esquema e o método de conexão acordados.
- **Alternativas consideradas:** Manter a configuração SQLite existente.
- **Motivo:** A equipe escolheu Supabase e atribuiu a responsabilidade pelo banco a um colega. Definir primeiro o esquema e o método de conexão evita implementar com base em um contrato presumido.

## 26/11/2026 — Modelo JPA inicial do chamado

- **Decisão:** Preparar a entidade JPA `Chamado` com base nos campos propostos para o relato, mantendo o e-mail fora do modelo de dados visível à secretaria. Manter a configuração SQLite existente para testes locais de mapeamento até que o esquema e o método de conexão com o Supabase sejam confirmados.
- **Alternativas consideradas:** Aguardar o esquema do banco antes de criar qualquer modelo de domínio ou trocar a configuração de conexão antes de o contrato do Supabase estar pronto.
- **Motivo:** Os campos principais do relato e os status já são conhecidos; seu mapeamento pode ser revisado e testado localmente sem tratar o esquema Supabase ou a API proposta como definitivos.

## 27/11/2026 — Escopo inicial de persistência do back-end

- **Decisão:** Usar SQLite/JPA no desenvolvimento local até o colega confirmar o esquema do Supabase; depois, integrar ao PostgreSQL do Supabase via JPA. O e-mail do reclamante é obrigatório e será armazenado em uma entidade privada de acompanhamento, separada de `Chamado`. Adiar o upload de fotos até que o Supabase Storage esteja pronto. Adiar o envio de códigos e os endpoints de acompanhamento até definir o provedor de e-mail e o ciclo de vida do código; adiar os endpoints da secretaria até integrar o Supabase Auth. Manter os campos de sugestão/revisão da IA, mas não alegar classificação real por IA antes da escolha de um provedor.
- **Alternativas consideradas:** Esperar o banco para iniciar todo o back-end, guardar o e-mail junto dos dados visíveis à secretaria, salvar fotos temporariamente no disco local ou publicar rotas sem os serviços de e-mail/autenticação necessários.
- **Motivo:** Permitir o avanço seguro da persistência local sem acoplar o back-end a um esquema não aprovado nem expor dados privados, evitando fluxos falsos de envio, acompanhamento, foto, IA ou login da equipe.
