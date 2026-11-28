(function () {
  "use strict";

  function initTheme() {
    const storageKey = "ouvidoria-campus-theme";
    const savedTheme = localStorage.getItem(storageKey);
    const preferredTheme = window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
    const theme = savedTheme === "dark" || savedTheme === "light" ? savedTheme : preferredTheme;

    function applyTheme(nextTheme) {
      document.documentElement.dataset.theme = nextTheme;
      const metaTheme = document.querySelector('meta[name="theme-color"]');
      if (metaTheme) {
        metaTheme.content = nextTheme === "dark" ? "#0b171f" : "#f3f7fa";
      }
      const toggle = document.querySelector("[data-theme-toggle]");
      if (toggle) {
        const isDark = nextTheme === "dark";
        toggle.textContent = isDark ? "☀" : "☾";
        toggle.setAttribute("aria-label", isDark ? "Ativar modo claro" : "Ativar modo escuro");
        toggle.setAttribute("title", isDark ? "Ativar modo claro" : "Ativar modo escuro");
        toggle.setAttribute("aria-pressed", String(isDark));
      }
    }

    applyTheme(theme);

    const nav = document.querySelector(".header-nav");
    if (!nav || nav.querySelector("[data-theme-toggle]")) {
      return;
    }

    const toggle = document.createElement("button");
    toggle.type = "button";
    toggle.className = "theme-toggle";
    toggle.dataset.themeToggle = "";
    nav.appendChild(toggle);

    toggle.addEventListener("click", function () {
      const nextTheme = document.documentElement.dataset.theme === "dark" ? "light" : "dark";
      localStorage.setItem(storageKey, nextTheme);
      applyTheme(nextTheme);
    });

    applyTheme(document.documentElement.dataset.theme);
  }

  function setFeedback(element, message, kind) {
    element.className = "feedback " + (kind || "info");
    element.textContent = message;
    element.hidden = false;
  }

  function showSuccess(result, protocol) {
    const heading = document.createElement("div");
    heading.className = "success-heading";

    const icon = document.createElement("span");
    icon.className = "success-icon";
    icon.setAttribute("aria-hidden", "true");
    icon.textContent = "✓";

    const titleGroup = document.createElement("div");
    const label = document.createElement("p");
    label.className = "step-label";
    label.textContent = "RELATO REGISTRADO";

    const title = document.createElement("h3");
    title.textContent = "Seu protocolo";
    titleGroup.append(label, title);
    heading.append(icon, titleGroup);

    const message = document.createElement("p");
    message.textContent = "Guarde o protocolo. Para acompanhar este relato, use o código padrão da demonstração: xy1234XY.";

    const protocolValue = document.createElement("p");
    protocolValue.className = "protocol-value";
    protocolValue.textContent = protocol;

    const trackingLink = document.createElement("a");
    trackingLink.className = "button-link";
    trackingLink.href = "consulta.html";
    trackingLink.textContent = "Acompanhar relato";

    result.replaceChildren(heading, message, protocolValue, trackingLink);
    result.className = "feedback success report-success";
    result.hidden = false;
  }

  function showFollowUpResult(result, report) {
    const statusLabels = {
      RECEBIDO: "Recebido",
      EM_ANDAMENTO: "Em andamento",
      RESOLVIDO: "Resolvido"
    };
    const statusClasses = {
      RECEBIDO: "recebido",
      EM_ANDAMENTO: "em-andamento",
      RESOLVIDO: "resolvido"
    };
    const card = document.createElement("section");
    card.className = "tracking-result";

    const heading = document.createElement("div");
    heading.className = "tracking-heading";
    const title = document.createElement("h3");
    title.textContent = "Relato encontrado";
    const status = document.createElement("span");
    status.className = "status-badge " + (statusClasses[report.status] || "");
    status.textContent = statusLabels[report.status] || report.status;
    heading.append(title, status);
    card.append(heading);

    const protocol = document.createElement("p");
    protocol.className = "tracking-location";
    protocol.textContent = "Protocolo: " + report.protocol;
    card.append(protocol);

    const metadata = document.createElement("div");
    metadata.className = "tracking-meta";
    [
      ["Criado em", report.createdAt],
      ["Atualizado em", report.updatedAt]
    ].forEach(function ([label, value]) {
      const date = new Date(value);
      if (Number.isNaN(date.getTime())) {
        return;
      }
      const item = document.createElement("span");
      const strong = document.createElement("strong");
      strong.textContent = label + ": ";
      item.append(strong, document.createTextNode(date.toLocaleString("pt-BR")));
      metadata.append(item);
    });
    card.append(metadata);

    const responseBox = document.createElement("div");
    responseBox.className = "response-box";
    const responseTitle = document.createElement("strong");
    responseTitle.textContent = "Resposta da equipe";
    const responseText = document.createElement("p");
    responseText.textContent = report.teamResponse || "A equipe ainda não adicionou uma resposta.";
    responseBox.append(responseTitle, responseText);
    card.append(responseBox);

    result.replaceChildren(card);
    result.className = "feedback";
    result.hidden = false;
  }

  function initReportForm() {
    const form = document.getElementById("report-form");
    if (!form) {
      return;
    }

    const result = document.getElementById("report-result");
    const button = form.querySelector('button[type="submit"]');
    const apiBase = document.querySelector('meta[name="api-base-url"]');
    const apiUrl = ((apiBase && apiBase.content.trim()) || window.location.origin).replace(/\/+$/, "");

    form.addEventListener("submit", async function (event) {
      event.preventDefault();
      if (!form.reportValidity()) {
        return;
      }

      const data = new FormData(form);
      const payload = {
        title: String(data.get("title") || "").trim(),
        description: String(data.get("description") || "").trim(),
        location: String(data.get("location") || "").trim(),
        email: String(data.get("email") || "").trim()
      };
      const buttonText = button.textContent;
      button.disabled = true;
      button.textContent = "Enviando...";
      result.hidden = true;

      try {
        const response = await fetch(apiUrl + "/api/chamados", {
          method: "POST",
          headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify(payload)
        });

        const responseText = await response.text();
        let body = null;
        if (responseText) {
          try {
            body = JSON.parse(responseText);
          } catch (error) {
            body = null;
          }
        }

        if (!response.ok) {
          if (response.status === 400) {
            throw new Error("Confira os campos e o formato do e-mail e tente novamente.");
          }
          if (response.status >= 500) {
            throw new Error("O servidor não conseguiu registrar o relato. Tente novamente mais tarde.");
          }
          throw new Error("Não foi possível registrar o relato (HTTP " + response.status + ").");
        }

        if (!body || typeof body.protocol !== "string" || !body.protocol) {
          throw new Error("O servidor respondeu sem um protocolo válido. Verifique o estado do relato antes de tentar novamente.");
        }

        form.reset();
        showSuccess(result, body.protocol);
      } catch (error) {
        const message = error instanceof TypeError
          ? "Não foi possível conectar ao backend. Verifique se ele está ativo e tente novamente."
          : error.message;
        setFeedback(result, message || "Não foi possível registrar o relato.", "error");
      } finally {
        button.disabled = false;
        button.textContent = buttonText;
      }
    });
  }

  function initFollowUpForm() {
    const form = document.getElementById("follow-up-form");
    if (!form) {
      return;
    }

    const result = document.getElementById("follow-up-result");
    const button = form.querySelector('button[type="submit"]');
    const apiBase = document.querySelector('meta[name="api-base-url"]');
    const apiUrl = ((apiBase && apiBase.content.trim()) || window.location.origin).replace(/\/+$/, "");

    form.addEventListener("submit", async function (event) {
      event.preventDefault();
      if (!form.reportValidity()) {
        return;
      }

      const data = new FormData(form);
      const payload = {
        protocol: String(data.get("protocol") || "").trim(),
        accessCode: String(data.get("accessCode") || "").trim()
      };
      const buttonText = button.textContent;
      button.disabled = true;
      button.textContent = "Consultando...";
      result.hidden = true;

      try {
        const response = await fetch(apiUrl + "/api/chamados/consulta", {
          method: "POST",
          headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify(payload)
        });

        if (response.status === 404) {
          throw new Error("Não localizamos o relato. Confira o protocolo e o código de acesso.");
        }
        if (response.status === 400) {
          throw new Error("Informe o protocolo e o código de acesso.");
        }
        if (!response.ok) {
          throw new Error("Não foi possível consultar o relato. Tente novamente mais tarde.");
        }

        const report = await response.json();
        if (!report || typeof report.protocol !== "string" || typeof report.status !== "string") {
          throw new Error("O servidor respondeu com dados inválidos. Tente novamente mais tarde.");
        }
        showFollowUpResult(result, report);
      } catch (error) {
        const message = error instanceof TypeError
          ? "Não foi possível conectar ao backend. Verifique se ele está ativo e tente novamente."
          : error.message;
        setFeedback(result, message || "Não foi possível consultar o relato.", "error");
      } finally {
        button.disabled = false;
        button.textContent = buttonText;
      }
    });
  }

  function initializeApp() {
    initTheme();
    initReportForm();
    initFollowUpForm();
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", initializeApp);
  } else {
    initializeApp();
  }
}());
