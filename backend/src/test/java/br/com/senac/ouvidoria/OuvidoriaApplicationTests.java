package br.com.senac.ouvidoria;

import br.com.senac.ouvidoria.model.Chamado;
import br.com.senac.ouvidoria.model.StatusChamado;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OuvidoriaApplicationTests {

	@Autowired
	private EntityManager entityManager;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void persistsReportWithDefaultStatusAndTimestamps() {
		Chamado chamado = new Chamado();
		chamado.setProtocolo("TEST-REPORT-001");
		chamado.setTitulo("Projetor com defeito");
		chamado.setDescricao("O projetor não liga.");
		chamado.setLocalizacao("Bloco B, sala 12");

		entityManager.persist(chamado);
		entityManager.flush();
		entityManager.clear();

		Chamado saved = entityManager.find(Chamado.class, chamado.getId());

		assertNotNull(saved.getId());
		assertEquals(StatusChamado.RECEBIDO, saved.getStatus());
		assertNotNull(saved.getCreatedAt());
		assertNotNull(saved.getUpdatedAt());
		assertEquals("Bloco B, sala 12", saved.getLocalizacao());
	}
}
