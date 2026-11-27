package br.com.senac.ouvidoria;

import br.com.senac.ouvidoria.model.AcompanhamentoPrivado;
import br.com.senac.ouvidoria.model.Chamado;
import br.com.senac.ouvidoria.model.StatusChamado;
import br.com.senac.ouvidoria.repository.AcompanhamentoPrivadoRepository;
import br.com.senac.ouvidoria.service.RegistrarChamadoService;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OuvidoriaApplicationTests {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RegistrarChamadoService registrarChamadoService;

	@Autowired
	private AcompanhamentoPrivadoRepository acompanhamentoRepository;

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
				.findByChamado_Id(chamado.getId())
				.orElseThrow();

		assertNotNull(saved.getId());
		assertEquals(StatusChamado.RECEBIDO, saved.getStatus());
		assertNotNull(saved.getCreatedAt());
		assertNotNull(saved.getUpdatedAt());
		assertEquals("Bloco A, sala 4", saved.getLocalizacao());
		assertEquals("reclamante@example.com", savedFollowUp.getEmail());
		assertEquals(chamado.getId(), savedFollowUp.getChamado().getId());
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
