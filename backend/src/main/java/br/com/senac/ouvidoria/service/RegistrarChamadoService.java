package br.com.senac.ouvidoria.service;

import br.com.senac.ouvidoria.model.AcompanhamentoPrivado;
import br.com.senac.ouvidoria.model.Chamado;
import br.com.senac.ouvidoria.repository.AcompanhamentoPrivadoRepository;
import br.com.senac.ouvidoria.repository.ChamadoRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class RegistrarChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final AcompanhamentoPrivadoRepository acompanhamentoRepository;
    private final EmailCipher emailCipher;

    public RegistrarChamadoService(
            ChamadoRepository chamadoRepository,
            AcompanhamentoPrivadoRepository acompanhamentoRepository,
            EmailCipher emailCipher) {
        this.chamadoRepository = chamadoRepository;
        this.acompanhamentoRepository = acompanhamentoRepository;
        this.emailCipher = emailCipher;
    }

    @Transactional
    public Chamado registrar(@NotNull Chamado chamado, @NotBlank @Email String email) {
        if (chamado.getId() != null) {
            throw new IllegalArgumentException("Only new reports can be registered.");
        }

        Chamado chamadoSalvo = chamadoRepository.save(chamado);

        AcompanhamentoPrivado acompanhamento = new AcompanhamentoPrivado();
        acompanhamento.setChamado(chamadoSalvo);
        acompanhamento.setEmailCifrado(emailCipher.encrypt(email));
        acompanhamentoRepository.save(acompanhamento);

        return chamadoSalvo;
    }
}
