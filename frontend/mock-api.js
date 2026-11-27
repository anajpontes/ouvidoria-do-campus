(function () {
  "use strict";

  const storageKey = "ouvidoria-campus-demo-v1";
  const authStorageKey = "ouvidoria-campus-demo-auth-v1";
  const demoCode = "246810";
  const demoUsername = "secretaria@demo.local";
  const demoPassword = "campus123";
  const statuses = ["recebido", "em andamento", "resolvido"];
  const types = [
    "Equipamento de sala",
    "Elétrico",
    "Limpeza",
    "Acessibilidade",
    "Hidráulica",
    "Sem classificação"
  ];
  const priorities = ["Alta", "Média", "Baixa", "Não definida"];

  const seedReports = [
    {
      id: "seed-001",
      protocol: "OUV-DEMO-001",
      title: "Projetor não liga",
      description: "O projetor da sala não liga durante a aula.",
      location: "Bloco A, sala 08",
      email: "estudante1@example.test",
      status: "recebido",
      type: "Equipamento de sala",
      priority: "Alta",
      suggestedType: "Equipamento de sala",
      suggestedPriority: "Alta",
      response: "",
      createdAt: "2026-11-25T13:00:00.000Z"
    },
    {
      id: "seed-002",
      protocol: "OUV-DEMO-002",
      title: "Lâmpadas queimadas no corredor",
      description: "Duas lâmpadas estão apagadas no corredor do segundo andar.",
      location: "Bloco B, segundo andar",
      email: "estudante2@example.test",
      status: "em andamento",
      type: "Elétrico",
      priority: "Média",
      suggestedType: "Elétrico",
      suggestedPriority: "Média",
      response: "Manutenção informada para avaliação.",
      createdAt: "2026-11-24T15:30:00.000Z"
    },
    {
      id: "seed-003",
      protocol: "OUV-DEMO-003",
      title: "Acesso à rampa obstruído",
      description: "Materiais estão bloqueando parcialmente a passagem da rampa.",
      location: "Entrada do Bloco C",
      email: "estudante3@example.test",
      status: "resolvido",
      type: "Acessibilidade",
      priority: "Alta",
      suggestedType: "Acessibilidade",
      suggestedPriority: "Alta",
      response: "Os materiais foram removidos da passagem.",
      createdAt: "2026-11-23T09:10:00.000Z"
    }
  ];

  function readReports() {
    const stored = sessionStorage.getItem(storageKey);
    if (stored === null) {
      sessionStorage.setItem(storageKey, JSON.stringify(seedReports));
      return seedReports.map(function (report) {
        return Object.assign({}, report);
      });
    }

    const parsed = JSON.parse(stored);
    if (!Array.isArray(parsed)) {
      throw new Error("Os dados locais da demonstração estão inválidos. Recarregue a página para reiniciar.");
    }
    return parsed;
  }

  function writeReports(reports) {
    sessionStorage.setItem(storageKey, JSON.stringify(reports));
  }

  function findByProtocol(protocol) {
    const normalized = String(protocol).trim().toUpperCase();
    return readReports().find(function (report) {
      return report.protocol.toUpperCase() === normalized;
    });
  }

  function publicReport(report) {
    const safeReport = Object.assign({}, report);
    delete safeReport.email;
    delete safeReport.code;
    return safeReport;
  }

  function createReport(input) {
    const reports = readReports();
    const suffix = Date.now().toString().slice(-6);
    const protocol = "OUV-DEMO-" + suffix;
    const report = {
      id: "report-" + Date.now(),
      protocol: protocol,
      title: input.title.trim(),
      description: input.description.trim(),
      location: input.location.trim(),
      email: input.email.trim(),
      status: "recebido",
      type: "Sem classificação",
      priority: "Não definida",
      suggestedType: "Equipamento de sala",
      suggestedPriority: "Média",
      response: "",
      createdAt: new Date().toISOString()
    };

    reports.unshift(report);
    writeReports(reports);
    return publicReport(report);
  }

  function requestCode(protocol) {
    const report = findByProtocol(protocol);
    if (!report) {
      throw new Error("Não encontramos esse protocolo na demonstração. Experimente OUV-DEMO-001 ou envie um novo relato.");
    }
    return { protocol: report.protocol };
  }

  function verifyCode(protocol, code) {
    const report = findByProtocol(protocol);
    if (!report) {
      throw new Error("Esse protocolo não está disponível na demonstração.");
    }
    if (String(code).trim() !== demoCode) {
      throw new Error("Código incorreto. Use o código fictício informado na tela.");
    }
    return publicReport(report);
  }

  function getQueue() {
    return readReports().map(publicReport);
  }

  function updateReport(id, updates) {
    if (statuses.indexOf(updates.status) === -1) {
      throw new Error("Status inválido para esta demonstração.");
    }
    if (types.indexOf(updates.type) === -1) {
      throw new Error("Tipo inválido para esta demonstração.");
    }
    if (priorities.indexOf(updates.priority) === -1) {
      throw new Error("Prioridade inválida para esta demonstração.");
    }

    const reports = readReports();
    const report = reports.find(function (item) {
      return item.id === id;
    });
    if (!report) {
      throw new Error("O relato não foi encontrado na demonstração.");
    }

    report.status = updates.status;
    report.type = updates.type;
    report.priority = updates.priority;
    report.response = String(updates.response || "").trim();
    report.updatedAt = new Date().toISOString();
    writeReports(reports);
    return publicReport(report);
  }

  function login(username, password) {
    if (String(username).trim().toLowerCase() !== demoUsername || password !== demoPassword) {
      throw new Error("Usuário ou senha incorretos. Use as credenciais fictícias exibidas nesta tela.");
    }
    sessionStorage.setItem(authStorageKey, "authenticated");
  }

  function isAuthenticated() {
    return sessionStorage.getItem(authStorageKey) === "authenticated";
  }

  function logout() {
    sessionStorage.removeItem(authStorageKey);
  }

  window.ComplaintDemoApi = Object.freeze({
    demoCode: demoCode,
    createReport: createReport,
    requestCode: requestCode,
    verifyCode: verifyCode,
    getQueue: getQueue,
    updateReport: updateReport,
    login: login,
    isAuthenticated: isAuthenticated,
    logout: logout
  });
}());
