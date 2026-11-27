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

- **Decisão:** usar Node.js com Express no back-end.
- **Alternativas consideradas:** Java com Spring Boot; Python com FastAPI.
- **Motivo:** o time já tem mais familiaridade com JavaScript, e o prazo de uma semana favorece uma stack na qual todos conseguem contribuir desde o primeiro dia.

## [data] — [título da decisão]

- **Decisão:**
- **Alternativas consideradas:**
- **Motivo:**

## 26/11/2026 — Exigir e-mail para acompanhar relatos

- **Decisão:** exigir um endereço de e-mail ao enviar uma reclamação para que o reclamante receba o protocolo e acompanhe as atualizações.
- **Alternativas consideradas:** manter o e-mail opcional, o que impediria acompanhar pelo protocolo e pelo fluxo de confirmação os relatos enviados sem e-mail.
- **Motivo:** o acompanhamento depende do envio de um código de confirmação para o e-mail associado à reclamação. O e-mail continua restrito ao acompanhamento pelo reclamante e não pode ficar visível para a equipe do campus.
