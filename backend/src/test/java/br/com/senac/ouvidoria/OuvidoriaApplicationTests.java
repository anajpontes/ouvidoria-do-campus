package br.com.senac.ouvidoria;

import br.com.senac.ouvidoria.model.AcompanhamentoPrivado;
import br.com.senac.ouvidoria.model.Chamado;
import br.com.senac.ouvidoria.model.StatusChamado;
import br.com.senac.ouvidoria.repository.AcompanhamentoPrivadoRepository;
import br.com.senac.ouvidoria.repository.ChamadoRepository;
import br.com.senac.ouvidoria.service.EmailCipher;
import br.com.senac.ouvidoria.service.RegistrarChamadoService;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.crypto.follow-up-email-key=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=")
@AutoConfigureMockMvc
class OuvidoriaApplicationTests {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RegistrarChamadoService registrarChamadoService;

	@Autowired
	private AcompanhamentoPrivadoRepository acompanhamentoRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private EmailCipher emailCipher;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void persistsReportWithDefaultStatusAndTimestamps() {
		Chamado chamado = novoChamado("TEST-REPORT-001");
		registrarChamadoService.registrar(chamado, "reclamante@example.com");
		entityManager.flush();
		entityManager.clear();

		Chamado saved = entityManager.find(Chamado.class, chamado.getId());
		AcompanhamentoPrivado savedFollowUp = acompanhamentoRepository
				.findById(chamado.getId())
				.orElseThrow();

		assertNotNull(saved.getId());
		assertEquals(StatusChamado.RECEBIDO, saved.getStatus());
		assertNotNull(saved.getCreatedAt());
		assertNotNull(saved.getUpdatedAt());
		assertEquals("Bloco A, sala 4", saved.getLocalizacao());
		assertNotEquals("reclamante@example.com", savedFollowUp.getEmailCifrado());
		assertEquals("reclamante@example.com", emailCipher.decrypt(savedFollowUp.getEmailCifrado()));
		assertEquals(chamado.getId(), savedFollowUp.getChamado().getId());
	}

	@Test
	@Transactional
	void publicSubmissionReturnsProtocolWithoutPrivateEmail() throws Exception {
		long countBefore = chamadoRepository.count();
		String response = mockMvc.perform(post("/api/chamados")
						.contentType(APPLICATION_JSON)
						.content("""
								{
								  "title": "Projetor com defeito",
								  "description": "O projetor não liga.",
								  "location": "Bloco B, sala 12",
								  "email": "reclamante@example.com"
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.protocol").isNotEmpty())
				.andExpect(jsonPath("$.status").value("RECEBIDO"))
				.andExpect(jsonPath("$.createdAt").isNotEmpty())
				.andExpect(jsonPath("$.email").doesNotExist())
				.andReturn()
				.getResponse()
				.getContentAsString();

		String protocol = objectMapper.readTree(response).get("protocol").asText();
		Chamado chamado = chamadoRepository.findByProtocolo(protocol).orElseThrow();
		AcompanhamentoPrivado acompanhamento = acompanhamentoRepository
				.findById(chamado.getId())
				.orElseThrow();

		assertEquals(countBefore + 1, chamadoRepository.count());
		assertEquals("reclamante@example.com", emailCipher.decrypt(
				acompanhamento.getEmailCifrado()));
	}

	@Test
	@Transactional
	void publicSubmissionRejectsInvalidEmail() throws Exception {
		long countBefore = chamadoRepository.count();

		mockMvc.perform(post("/api/chamados")
						.contentType(APPLICATION_JSON)
						.content("""
								{
								  "title": "Projetor com defeito",
								  "description": "O projetor não liga.",
								  "location": "Bloco B, sala 12",
								  "email": "not-an-email"
								}
								"""))
				.andExpect(status().isBadRequest());

		assertEquals(countBefore, chamadoRepository.count());
	}

	@Test
	@Transactional
	void rejectsInvalidFollowUpEmailBeforePersistingReport() {
		Chamado missingEmail = novoChamado("TEST-REPORT-002");
		Chamado malformedEmail = novoChamado("TEST-REPORT-003");

		assertThrows(ConstraintViolationException.class, () -> {
			registrarChamadoService.registrar(missingEmail, " ");
		});
		assertNull(missingEmail.getId());
		assertThrows(ConstraintViolationException.class, () -> {
			registrarChamadoService.registrar(malformedEmail, "not-an-email");
		});
		assertNull(malformedEmail.getId());
	}

	@Test
	@Transactional
	void rejectsRegisteringAnAlreadyPersistedReport() {
		Chamado chamado = novoChamado("TEST-REPORT-004");
		entityManager.persist(chamado);
		entityManager.flush();

		assertThrows(IllegalArgumentException.class, () -> {
			registrarChamadoService.registrar(chamado, "reclamante@example.com");
		});
	}

	private Chamado novoChamado(String protocolo) {
		Chamado chamado = new Chamado();
		chamado.setProtocolo(protocolo);
		chamado.setTitulo("Problema no campus");
		chamado.setDescricao("Descrição do problema.");
		chamado.setLocalizacao("Bloco A, sala 4");
		return chamado;
	}
}
