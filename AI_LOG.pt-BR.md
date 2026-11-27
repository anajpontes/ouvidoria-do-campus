# Registro de uso de IA (AI_LOG)

[English](AI_LOG.md) · **Português (Brasil)**

Registrem aqui como o time usou inteligência artificial no projeto. O uso de IA é liberado e incentivado, desde que documentado: este arquivo é entregável obrigatório e serve de roteiro para o bloco “IA na prática” da apresentação.

A documentação do projeto deve ser escrita **em inglês** (a versão principal é o [`AI_LOG.md`](AI_LOG.md)), com uma **versão em português** ao lado. Mantenham as duas versões alinhadas.

Mantenham este arquivo na **raiz** do repositório. Incluam as ferramentas utilizadas e, para cada uso relevante, o prompt principal, o que a IA gerou, o que o time aproveitou e o que foi corrigido ou rejeitado — e por quê.

Nunca commitem chaves de API, senhas ou outros segredos. Esses valores vão em `.env` (fora do repositório) e em GitHub Secrets.

Copiem o modelo abaixo sempre que precisarem de uma entrada nova.

## Ferramentas

- (ex.: ChatGPT, GitHub Copilot, Cursor, outro)

## Modelo de registro

```markdown
### [data] — [tarefa ou trecho]
- **Prompt principal:**
- **O que a IA gerou:**
- **O que o time aproveitou:**
- **O que o time corrigiu ou rejeitou, e por quê:**
```

## Registro

### 26/11/2026 — Identidade visual Senac e refinamento das telas
- **Prompt principal:** "Quero melhorar o design das telas, coloque a logo do Senac."
- **O que a IA gerou:** paleta azul atualizada, navegação responsiva e cartões refinados nas telas do front-end, com um arquivo local da logo Senac.
- **O que o time aproveitou:** o arquivo oficial da logo foi obtido no site do Senac (`https://www.senac.br/assets/svg/logo.svg`) e incluído localmente para que as páginas não dependam de um servidor externo para exibi-la.
- **O que o time corrigiu ou rejeitou, e por quê:** os avisos e interações da demonstração foram mantidos; a atualização visual não afirma vínculo além do uso da logo solicitada nem altera o comportamento mockado.

### 26/11/2026 — Mock interativo do front-end
- **Prompt principal:** "Desenvolva o front-end de forma mockada do back-end."
- **O que a IA gerou:** demonstração local interativa para abertura de reclamação, acompanhamento por protocolo/código e fila da equipe com filtros e ações de revisão, além de um login simulado para a equipe.
- **O que o time aproveitou:** a demonstração está identificada, usa registros fictícios, mantém estado somente na sessão do navegador, oculta o e-mail do reclamante na fila e não afirma enviar e-mails, fotos, oferecer autenticação real ou executar IA.
- **O que o time corrigiu ou rejeitou, e por quê:** não foram criados endpoints reais, regras de autenticação, ciclo de vida do código, armazenamento de fotos ou lógica de classificação por IA; isso continua dependendo do back-end e de decisões do produto.

### 26/11/2026 — Exigir e-mail em novas reclamações
- **Prompt principal:** "A tela de novo relato está errada; o email é obrigatório."
- **O que a IA gerou:** atualização do formulário para exigir e-mail e alinhamento do guia de continuidade e do registro bilíngue de decisões.
- **O que o time aproveitou:** o e-mail obrigatório permite enviar o protocolo e confirmar o acompanhamento depois; o e-mail permanece oculto para a equipe do campus.
- **O que o time corrigiu ou rejeitou, e por quê:** a foto continua opcional e nenhuma ação de API foi adicionada; o envio permanece desabilitado até a integração real com o back-end.

### 26/11/2026 — Correção do fluxo de acompanhamento
- **Prompt principal:** "Corrija a tela de acompanhamento para pedir somente o protocolo e permitir que o back-end envie o código."
- **O que a IA gerou:** a substituição dos campos de e-mail e código por um campo de protocolo, mantendo a tela visualmente consistente.
- **O que o time aproveitou:** a tela agora representa corretamente que o protocolo identifica o reclamante e que o back-end realiza o envio do código.
- **O que o time corrigiu ou rejeitou, e por quê:** mantivemos o botão desabilitado e não criamos regras ou dados simulados, pois o fluxo ainda depende da API da equipe.

### 26/11/2026 — Tela visual de acompanhamento de relato
- **Prompt principal:** "Continue o front-end do projeto usando o guia de continuidade e implemente a tela de consulta/acompanhamento sem assumir decisões pendentes de código ou autenticação."
- **O que a IA gerou:** navegação entre o novo relato e o acompanhamento, além da estrutura visual para informar o e-mail e confirmar um código.
- **O que o time aproveitou:** a nova tela mantém o mesmo padrão visual, explica o acompanhamento por e-mail e deixa claros os estados pendentes de integração.
- **O que o time corrigiu ou rejeitou, e por quê:** não foram criados endpoints, formatos de código, expiração, reenvio ou dados simulados, porque o guia exige uma decisão explícita da equipe antes dessas regras.

### 24/11/2026 — Autenticação JWT (exemplo)

- **Prompt principal:** "Gere um middleware de autenticação JWT em Node.js/Express, com tratamento de token expirado e uma rota protegida de exemplo."
- **O que a IA gerou:** um middleware e um exemplo de rota, com uma dependência desatualizada e sem validação de variáveis de ambiente.
- **O que o time aproveitou:** a estrutura do middleware e o fluxo de verificação do token.
- **O que o time corrigiu ou rejeitou, e por quê:** atualizamos a biblioteca, movemos o segredo para variável de ambiente e incluímos testes. O exemplo de rota foi reescrito para se adequar ao nosso modelo de usuário.

### [data] — [tarefa ou trecho]

- **Prompt principal:**
- **O que a IA gerou:**
- **O que o time aproveitou:**
- **O que o time corrigiu ou rejeitou, e por quê:**
