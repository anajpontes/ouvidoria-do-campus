package br.com.senac.ouvidoria.repository;

import br.com.senac.ouvidoria.model.AcompanhamentoPrivado;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcompanhamentoPrivadoRepository
        extends JpaRepository<AcompanhamentoPrivado, UUID> {
}
