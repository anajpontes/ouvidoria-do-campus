package br.com.senac.ouvidoria.repository;

import br.com.senac.ouvidoria.model.Chamado;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, UUID> {
}
