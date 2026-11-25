# Briefing do tema: Ouvidoria do campus

[English](BRIEFING.md) · **Português (Brasil)**

**Hack com IA: Codando com Inteligência**  
Senac na Prática · Técnico em Desenvolvimento de Sistemas  
Área: vida no campus e comunidade  
Imersão de 23/11 a 29/11 · apresentações em 30/11 e 01/12

Este briefing é o ponto de partida do **seu** tema. Leiam com calma, recortem um MVP que caiba em uma semana e registrem as decisões. Vocês vão transformar este problema em um produto real, com front-end e back-end, repositório organizado e uso documentado de IA. Esta é uma imersão colaborativa: a banca avalia o que o time construiu, decidiu e aprendeu.

## Tema e contexto

**Ouvidoria do campus** — Canal para reportar problemas de infraestrutura, com IA classificando e priorizando chamados.

Ar-condicionado que não gela, projetor que falha no começo da aula, acessibilidade precária, banheiro sem manutenção, tomada que não funciona no laboratório: o problema aparece, alguém comenta no grupo ou na recepção, e depois falta retorno. Sem um canal claro, o chamado se perde, o mesmo defeito é relatado várias vezes, e quem avisou não sabe se a demanda foi recebida, priorizada ou resolvida.

Vocês vão criar um canal objetivo para reportar problemas de infraestrutura do campus, em que a IA ajuda a classificar e a priorizar, e o status volta para quem abriu o chamado.

## Público e personas

- **Estudante ou funcionário que identifica um problema:** quer relatar rápido, com local e, se possível, uma foto, e acompanhar o andamento.
- **Quem recebe e organiza os chamados (papel de equipe do campus no MVP):** precisa de uma fila classificada, com prioridade e possíveis duplicatas.
- **A comunidade do campus:** se beneficia quando problemas recorrentes deixam de ficar invisíveis.

## Objetivo

Entregar um MVP de canal de chamados de infraestrutura em que a pessoa abre um relato, a IA classifica e sugere uma prioridade, a equipe acompanha a fila e o autor vê o status evoluir até o encerramento.

## Requisitos mínimos (MVP)

O recorte abaixo é realista para uma semana. Fechem-no no primeiro dia e registrem no `DECISIONS.md` o que entra, o que fica de fora e por quê. É melhor um fluxo completo e demonstrável do que uma lista longa pela metade.

1. Abrir um chamado com texto, local no campus e, se possível, foto.
2. A IA classifica o tipo (por exemplo, elétrico, limpeza, acessibilidade, equipamento de sala) e sugere uma prioridade, para revisão humana.
3. Há uma visão de fila para o papel de equipe do campus, com filtros por status, tipo e prioridade.
4. Atualizar o status (recebido, em andamento, resolvido) e registrar uma resposta curta.
5. Quem abriu o chamado acompanha o próprio ticket.
6. Oferecer a opção de relato identificado ou com menos dados pessoais à mostra, deixando claro o que isso implica no acompanhamento.

## Onde a IA entra

O uso de IA é liberado e incentivado — no produto e no processo — e precisa aparecer no `AI_LOG.md`. Algumas frentes concretas neste tema:

- Classificar o chamado e extrair o local a partir do texto, quando a pessoa não tiver escolhido um ponto da lista.
- Sugerir prioridade com um critério simples e explícito (por exemplo, risco à segurança versus desconforto pontual).
- Detectar chamados parecidos ainda abertos, para reduzir duplicatas.
- Ajudar a redigir o relato quando a descrição estiver vaga demais para agir.

Não publiquem, sem revisão de vocês, um texto ou uma decisão automática da IA como se já fosse a versão final.

## Extras opcionais

Só depois que o MVP estiver em pé. São ideias, não obrigações:

- Um mapa simples dos pontos mais relatados.
- Contagem por categoria na semana.
- Pesquisa curta de satisfação depois que o chamado é encerrado.
- Prazo de referência (um “até quando olhamos isso”) definido pela equipe, não pela IA sozinha.

## Fora de escopo

Para proteger o prazo e o foco do produto:

- Ouvidoria geral de questões acadêmicas, de recursos humanos ou de conduta — o MVP é infraestrutura do campus.
- Processos legais, denúncias formais de outra natureza e substituição de canais oficiais do Senac para esses temas.
- Fechar automaticamente um chamado só com a classificação da IA, sem alguém da equipe.
- Integração obrigatória com um sistema interno já existente da instituição.

## Perguntas para guiar o time

Usem estas perguntas no primeiro dia, ao definir o MVP, e de novo antes da demo:

- No MVP, quem representa a “equipe do campus” — um perfil de usuário é suficiente para a demo?
- Como vocês tratam relatos anônimos ou com poucos dados, e o que a pessoa ainda consegue acompanhar?
- O que impede um volume de relatos falsos ou repetidos de poluir a fila?
- Como a prioridade sugerida pela IA é apresentada, para não parecer uma decisão já tomada?
- O que caracteriza um chamado encerrado, e quem pode reabri-lo?

## Entregáveis e como a banca avalia

Esta é uma **imersão colaborativa** de uma semana, com times de 3 ou 4 estudantes do TDS. A banca avalia o trabalho de **vocês**, no próprio projeto — um retorno formativo, não uma comparação com outros times.

**O que precisa estar pronto no repositório** (raiz), em **inglês** (versão principal) e em **português** (`.pt-BR`), com o link entre os idiomas no topo de cada arquivo:

- `README.md` e `README.pt-BR.md`
- `DECISIONS.md` e `DECISIONS.pt-BR.md`
- `AI_LOG.md` e `AI_LOG.pt-BR.md`

A aplicação precisa ter **front-end e back-end**. O back-end pode ser **Java**, **Python** ou **Node.js** (isolados ou combinados, desde que integrados). O front-end é obrigatório (web, desktop ou mobile). Justifiquem a stack no `DECISIONS.md`. Também são obrigatórios o histórico com Conventional Commits, o trabalho via Pull Request com revisão, a proteção da `main` e uma pipeline de **GitHub Actions** verde (build, lint e testes) em PRs e na `main`. Não commitem segredos. No primeiro dia, adicionem o professor como colaborador: **adrianoanschau@gmail.com**.

O **congelamento do código** é no domingo, **29/11, às 23h59**. As apresentações são em **15 minutos + 5 minutos de perguntas**, nos dias 30/11 e 01/12, no Senac de Canoas, Sala 103. Ensaiem a demo e o roteiro (problema e solução, demo ao vivo, fluxo de trabalho, decisões, IA na prática e CI/CD).

A avaliação tem **duas bancas, com o mesmo peso**:

- **Banca Técnica (50%):** qualidade de código e arquitetura; Git, Conventional Commits e PRs; CI/CD; domínio do código nas perguntas.
- **Banca de Inovação e Impacto (50%):** originalidade; impacto (o problema real e para quem); experiência do usuário; clareza do pitch; uso criativo da IA.

Qualquer pessoa do time pode ser perguntada sobre qualquer parte do projeto. Usem a IA com inteligência, registrem o que ela gerou e o que vocês corrigiram, e assumam o código que forem apresentar.

Bom trabalho — e que a semana seja intensa, colaborativa e bem documentada.

