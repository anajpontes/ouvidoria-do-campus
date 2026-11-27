(function () {
  "use strict";

  const api = window.ComplaintDemoApi;
  const categories = [
    "Equipamento de sala",
    "Elétrico",
    "Limpeza",
    "Acessibilidade",
    "Hidráulica",
    "Sem classificação"
  ];
  const priorities = ["Alta", "Média", "Baixa", "Não definida"];
  const statuses = ["recebido", "em andamento", "resolvido"];
  const statusLabels = {
    recebido: "Recebido",
    "em andamento": "Em andamento",
    resolvido: "Resolvido"
  };

  function escapeHtml(value) {
    return String(value == null ? "" : value).replace(/[&<>"']/g, function (character) {
      return {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;"
      }[character];
    });
  }

  function setFeedback(element, message, kind) {
    element.className = "feedback " + (kind || "info");
    element.textContent = message;
    element.hidden = false;
  }

  function formatDate(value) {
    return new Intl.DateTimeFormat("pt-BR", {
      dateStyle: "medium",
      timeStyle: "short"
    }).format(new Date(value));
  }

  function statusClass(status) {
    return status.replace(/\s+/g, "-");
  }

  function initReportForm() {
    const form = document.getElementById("report-form");
    if (!form) {
      return;
    }

    const result = document.getElementById("report-result");
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      if (!form.reportValidity()) {
        return;
      }

      const data = new FormData(form);
      try {
        const report = api.createReport({
          title: data.get("title"),
          description: data.get("description"),
          location: data.get("location"),
          email: data.get("email")
        });
        const photoSelected = Boolean(data.get("photo") && data.get("photo").name);
        result.innerHTML =
          '<div class="success-heading"><span class="success-icon" aria-hidden="true">✓</span><div><p class="step-label">RELATO REGISTRADO · SIMULAÇÃO</p><h3>Seu protocolo está pronto</h3></div></div>' +
          '<p>Guarde este número para consultar o andamento. Nenhum e-mail foi enviado de verdade.</p>' +
          '<p class="protocol-value">' + escapeHtml(report.protocol) + '</p>' +
          (photoSelected ? '<p class="field-hint">A foto selecionada não é enviada nem salva nesta demonstração.</p>' : "") +
          '<a class="button-link" href="consulta.html?protocolo=' + encodeURIComponent(report.protocol) + '">Acompanhar relato</a>';
        result.className = "feedback success report-success";
        result.hidden = false;
        form.reset();
      } catch (error) {
        setFeedback(result, error.message || "Não foi possível registrar o relato de demonstração.", "error");
      }
    });
  }

  function renderTracking(report, target) {
    const response = report.response
      ? '<div class="response-box"><strong>Resposta da equipe</strong><p>' + escapeHtml(report.response) + '</p></div>'
      : '<p class="field-hint">Ainda não há uma resposta registrada para este relato.</p>';
    target.innerHTML =
      '<div class="tracking-heading"><div><p class="step-label">RELATO ENCONTRADO · DEMONSTRAÇÃO</p><h3>' + escapeHtml(report.title) + '</h3></div>' +
      '<span class="status-badge ' + escapeHtml(statusClass(report.status)) + '">' + escapeHtml(statusLabels[report.status] || report.status) + '</span></div>' +
      '<p class="tracking-location">' + escapeHtml(report.location) + ' · Protocolo ' + escapeHtml(report.protocol) + '</p>' +
      '<p>' + escapeHtml(report.description) + '</p>' +
      '<div class="tracking-meta"><span><strong>Tipo:</strong> ' + escapeHtml(report.type) + '</span><span><strong>Prioridade:</strong> ' + escapeHtml(report.priority) + '</span><span><strong>Aberto:</strong> ' + escapeHtml(formatDate(report.createdAt)) + '</span></div>' +
      response;
    target.hidden = false;
  }

  function initTracking() {
    const protocolForm = document.getElementById("protocol-form");
    if (!protocolForm) {
      return;
    }

    const protocolInput = document.getElementById("protocol");
    const protocolResult = document.getElementById("protocol-result");
    const codeForm = document.getElementById("code-form");
    const trackingResult = document.getElementById("tracking-result");

    const queryProtocol = new URLSearchParams(window.location.search).get("protocolo");
    if (queryProtocol) {
      protocolInput.value = queryProtocol;
    }

    protocolForm.addEventListener("submit", function (event) {
      event.preventDefault();
      if (!protocolForm.reportValidity()) {
        return;
      }

      try {
        const result = api.requestCode(protocolInput.value);
        protocolForm.dataset.protocol = result.protocol;
        setFeedback(
          protocolResult,
          "Código de demonstração solicitado para o e-mail vinculado ao protocolo. Nenhuma mensagem foi enviada. Código fictício: " + api.demoCode,
          "info"
        );
        codeForm.hidden = false;
        trackingResult.hidden = true;
        document.getElementById("verification-code").focus();
      } catch (error) {
        codeForm.hidden = true;
        setFeedback(protocolResult, error.message || "Não foi possível localizar o protocolo.", "error");
      }
    });

    codeForm.addEventListener("submit", function (event) {
      event.preventDefault();
      if (!codeForm.reportValidity()) {
        return;
      }

      try {
        const report = api.verifyCode(protocolForm.dataset.protocol, new FormData(codeForm).get("code"));
        renderTracking(report, trackingResult);
      } catch (error) {
        setFeedback(protocolResult, error.message || "Não foi possível confirmar o código.", "error");
      }
    });
  }

  function optionList(values, selected) {
    return values.map(function (value) {
      return '<option value="' + escapeHtml(value) + '"' + (value === selected ? " selected" : "") + ">" + escapeHtml(value) + "</option>";
    }).join("");
  }

  function renderQueueCard(report) {
    const suggestion = report.suggestedType + " · " + report.suggestedPriority;
    return '<article class="ticket-card">' +
      '<div class="ticket-topline"><span class="ticket-protocol">' + escapeHtml(report.protocol) + '</span><span class="status-badge ' + escapeHtml(statusClass(report.status)) + '">' + escapeHtml(statusLabels[report.status] || report.status) + '</span></div>' +
      '<div class="ticket-title-row"><div><h3>' + escapeHtml(report.title) + '</h3><p class="ticket-location">' + escapeHtml(report.location) + ' · ' + escapeHtml(formatDate(report.createdAt)) + '</p></div></div>' +
      '<p class="ticket-description">' + escapeHtml(report.description) + '</p>' +
      '<p class="suggestion-note"><strong>Sugestão simulada (não é IA):</strong> ' + escapeHtml(suggestion) + '. Revise antes de salvar.</p>' +
      '<form class="ticket-review" data-review-form data-id="' + escapeHtml(report.id) + '">' +
      '<div class="field"><label for="status-' + escapeHtml(report.id) + '">Status</label><select id="status-' + escapeHtml(report.id) + '" name="status">' + optionList(statuses, report.status) + '</select></div>' +
      '<div class="field"><label for="type-' + escapeHtml(report.id) + '">Tipo revisado</label><select id="type-' + escapeHtml(report.id) + '" name="type">' + optionList(categories, report.type) + '</select></div>' +
      '<div class="field"><label for="priority-' + escapeHtml(report.id) + '">Prioridade revisada</label><select id="priority-' + escapeHtml(report.id) + '" name="priority">' + optionList(priorities, report.priority) + '</select></div>' +
      '<div class="field response-field"><label for="response-' + escapeHtml(report.id) + '">Resposta curta</label><textarea id="response-' + escapeHtml(report.id) + '" name="response" rows="2" maxlength="240" placeholder="Ex.: Manutenção agendada para avaliação.">' + escapeHtml(report.response) + '</textarea></div>' +
      '<div class="ticket-actions"><span class="field-hint">Sem dados de e-mail do reclamante.</span><button type="submit">Salvar revisão</button></div>' +
      '<p class="save-feedback" data-save-feedback aria-live="polite" hidden></p>' +
      '</form></article>';
  }

  function initQueue() {
    const list = document.getElementById("queue-list");
    if (!list) {
      return;
    }
    if (!api.isAuthenticated()) {
      window.location.replace("equipe-login.html");
      return;
    }

    const filterForm = document.getElementById("filter-form");
    const visibleCount = document.getElementById("visible-count");
    const logoutButton = document.getElementById("logout-button");
    let reports;

    logoutButton.addEventListener("click", function () {
      api.logout();
      window.location.replace("equipe-login.html");
    });

    function render() {
      const status = filterForm.elements.status.value;
      const type = filterForm.elements.type.value;
      const priority = filterForm.elements.priority.value;
      const visible = reports.filter(function (report) {
        return (!status || report.status === status) &&
          (!type || report.type === type) &&
          (!priority || report.priority === priority);
      });

      visibleCount.textContent = "(" + visible.length + ")";
      list.innerHTML = visible.length
        ? visible.map(renderQueueCard).join("")
        : '<div class="empty-state"><strong>Nenhum relato encontrado</strong><p>Altere ou limpe os filtros para ver outros relatos.</p></div>';
    }

    try {
      reports = api.getQueue();
      document.getElementById("total-count").textContent = reports.length;
      document.getElementById("received-count").textContent = reports.filter(function (item) { return item.status === "recebido"; }).length;
      document.getElementById("progress-count").textContent = reports.filter(function (item) { return item.status === "em andamento"; }).length;
      document.getElementById("resolved-count").textContent = reports.filter(function (item) { return item.status === "resolvido"; }).length;
      render();
    } catch (error) {
      setFeedback(list, error.message || "Não foi possível carregar os relatos de demonstração.", "error");
    }

    filterForm.addEventListener("change", render);
    filterForm.addEventListener("reset", function () {
      window.setTimeout(render, 0);
    });

    list.addEventListener("submit", function (event) {
      const form = event.target.closest("[data-review-form]");
      if (!form) {
        return;
      }
      event.preventDefault();

      const data = new FormData(form);
      const feedback = form.querySelector("[data-save-feedback]");
      try {
        api.updateReport(form.dataset.id, {
          status: data.get("status"),
          type: data.get("type"),
          priority: data.get("priority"),
          response: data.get("response")
        });
        reports = api.getQueue();
        document.getElementById("received-count").textContent = reports.filter(function (item) { return item.status === "recebido"; }).length;
        document.getElementById("progress-count").textContent = reports.filter(function (item) { return item.status === "em andamento"; }).length;
        document.getElementById("resolved-count").textContent = reports.filter(function (item) { return item.status === "resolvido"; }).length;
        render();
        const updated = list.querySelector('[data-id="' + CSS.escape(form.dataset.id) + '"] [data-save-feedback]');
        if (updated) {
          setFeedback(updated, "Revisão salva nesta sessão de demonstração.", "success");
        }
      } catch (error) {
        setFeedback(feedback, error.message || "Não foi possível salvar a revisão.", "error");
      }
    });
  }

  function initTeamLogin() {
    const form = document.getElementById("team-login-form");
    if (!form) {
      return;
    }

    if (api.isAuthenticated()) {
      window.location.replace("fila.html");
      return;
    }

    const result = document.getElementById("login-result");
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      if (!form.reportValidity()) {
        return;
      }

      const data = new FormData(form);
      try {
        api.login(data.get("username"), data.get("password"));
        window.location.replace("fila.html");
      } catch (error) {
        setFeedback(result, error.message || "Não foi possível iniciar a sessão demonstrativa.", "error");
      }
    });
  }

  document.addEventListener("DOMContentLoaded", function () {
    initReportForm();
    initTracking();
    initTeamLogin();
    initQueue();
  });
}());
