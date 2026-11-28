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

### 27/11/2026 — Reduzir a largura do aviso de demonstração
- **Prompt principal:** "Diminua a largura do aviso de demonstração local."
- **O que a IA gerou:** um aviso centralizado com largura máxima de 760 px e margens responsivas.
- **O que o time aproveitou:** o mesmo estilo é aplicado aos avisos de demonstração nas telas para manter consistência.
- **O que o time corrigiu ou rejeitou, e por quê:** o texto e as cores existentes foram preservados.

### 27/11/2026 — Substituir a logo Senac pela arte enviada
- **Prompt principal:** "Utilize este estilo de logo, com o fundo transparente."
- **O que a IA gerou:** uma cópia recortada da imagem PNG enviada, preservando a transparência, e a atualização da logo nos quatro cabeçalhos.
- **O que o time aproveitou:** a arte fornecida foi mantida sem alterações; o recorte remove apenas margens transparentes para melhor encaixe.
- **O que o time corrigiu ou rejeitou, e por quê:** a moldura colorida anterior foi removida para não competir com a logo escolhida.

### 27/11/2026 — Aplicar a paleta da interface ao redor da logo Senac
- **Prompt principal:** "Mude a logo do Senac para laranja, azul e cinza."
- **O que a IA gerou:** uma moldura em laranja, azul e cinza ao redor da logo Senac incluída localmente.
- **O que o time aproveitou:** a arte oficial da logo permanece inalterada; somente a moldura da interface usa as cores solicitadas.
- **O que o time corrigiu ou rejeitou, e por quê:** a marca oficial não foi recolorida para preservar sua identidade visual original.

### 27/11/2026 — Reforçar fundos e aumentar um pouco os textos
- **Prompt principal:** "Deixe as cores mais fortes ainda e aumente um pouco o tamanho dos textos."
- **O que a IA gerou:** fundos azul-acinzentados e laranjas mais intensos e aumentos moderados na navegação, nos rótulos dos formulários, no texto principal, nos filtros e nos metadados.
- **O que o time aproveitou:** campos de entrada brancos e significados das cores dos status foram mantidos para preservar a clareza.
- **O que o time corrigiu ou rejeitou, e por quê:** nenhum comportamento ou fluxo foi alterado; as larguras responsivas foram verificadas após os ajustes de tipografia.

### 27/11/2026 — Reforçar cores de fundo das telas
- **Prompt principal:** "Aumente as cores de fundo, não deixe tudo branco."
- **O que a IA gerou:** fundos cinza-azulados mais marcantes, cartões de formulário e acompanhamento azulados, seção de e-mail e alguns chamados em tons de laranja, além de cartões-resumo variados.
- **O que o time aproveitou:** os campos de entrada continuam claros para facilitar a leitura; cores dos status e interações foram mantidas.
- **O que o time corrigiu ou rejeitou, e por quê:** não foram adicionadas dependências nem novos fluxos; é um refinamento visual somente em CSS.

### 27/11/2026 — Mais detalhes visuais em azul, laranja e cinza
- **Prompt principal:** "Coloque mais cor nas telas, deixando mais bonito, utilizando azul, laranja e cinza."
- **O que a IA gerou:** refinamento dos estilos compartilhados com fundo cinza-azulado, superfícies azuladas nos cabeçalhos e na fila, detalhes laranja e cartões-resumo com mais contraste.
- **O que o time aproveitou:** o layout, as interações e as cores semânticas dos status existentes foram preservados.
- **O que o time corrigiu ou rejeitou, e por quê:** não foram adicionados fluxos de tela ou dependências; somente os estilos visuais compartilhados foram ajustados.

### 27/11/2026 — Aumentar tamanho dos status
- **Prompt principal:** "Aumente o tamanho das letras para o status."
- **O que a IA gerou:** aumento do tamanho e do espaçamento dos badges de status, sem alterar os demais rótulos compactos.
- **O que o time aproveitou:** os status maiores ficam mais visíveis na fila da equipe e no acompanhamento.
- **O que o time corrigiu ou rejeitou, e por quê:** nenhuma cor, comportamento ou lógica da API foi alterada.

### 27/11/2026 — Cores dos status na fila da equipe
- **Prompt principal:** "Na tela da equipe, colorir o status do problema de laranja para recebido, vermelho para em andamento e verde para resolvido."
- **O que a IA gerou:** atualização das cores dos badges de status para laranja, vermelho e verde, com contraste legível entre texto e fundo.
- **O que o time aproveitou:** o mesmo estilo de status aparece na fila da equipe e na tela de acompanhamento do reclamante.
- **O que o time corrigiu ou rejeitou, e por quê:** nenhuma regra de status ou lógica de API foi alterada; a mudança é somente visual.

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
