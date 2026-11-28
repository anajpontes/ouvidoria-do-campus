package br.com.senac.ouvidoria.service;

import br.com.senac.ouvidoria.model.Chamado;
import br.com.senac.ouvidoria.repository.ChamadoRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsultarChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final byte[] accessCode;

    public ConsultarChamadoService(
            ChamadoRepository chamadoRepository,
            @Value("${app.follow-up.access-code:xy1234XY}") String accessCode) {
        this.chamadoRepository = chamadoRepository;
        this.accessCode = accessCode.getBytes(StandardCharsets.UTF_8);
    }

    public Optional<Chamado> consultar(String protocol, String submittedAccessCode) {
        byte[] submittedCode = submittedAccessCode.getBytes(StandardCharsets.UTF_8);
        if (!MessageDigest.isEqual(accessCode, submittedCode)) {
            return Optional.empty();
        }

        return chamadoRepository.findByProtocolo(protocol);
    }
}
