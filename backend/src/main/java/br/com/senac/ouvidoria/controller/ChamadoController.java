package br.com.senac.ouvidoria.controller;

import br.com.senac.ouvidoria.model.Chamado;
import br.com.senac.ouvidoria.service.ConsultarChamadoService;
import br.com.senac.ouvidoria.service.RegistrarChamadoService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    private final RegistrarChamadoService registrarChamadoService;
    private final ConsultarChamadoService consultarChamadoService;

    public ChamadoController(
            RegistrarChamadoService registrarChamadoService,
            ConsultarChamadoService consultarChamadoService) {
        this.registrarChamadoService = registrarChamadoService;
        this.consultarChamadoService = consultarChamadoService;
    }

    @PostMapping
    public ResponseEntity<ChamadoCriadoResponse> criar(
            @Valid @RequestBody CriarChamadoRequest request) {
        Chamado chamado = new Chamado();
        chamado.setProtocolo(UUID.randomUUID().toString());
        chamado.setTitulo(request.title().strip());
        chamado.setDescricao(request.description().strip());
        chamado.setLocalizacao(request.location().strip());

        Chamado criado = registrarChamadoService.registrar(chamado, request.email().strip());
        ChamadoCriadoResponse response = new ChamadoCriadoResponse(
                criado.getProtocolo(), criado.getStatus(), criado.getCreatedAt());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/consulta")
    public ResponseEntity<AcompanhamentoChamadoResponse> consultar(
            @Valid @RequestBody ConsultarChamadoRequest request) {
        var encontrado = consultarChamadoService.consultar(
                request.protocol().strip(), request.accessCode().strip());
        if (encontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Chamado chamado = encontrado.get();
        AcompanhamentoChamadoResponse response = new AcompanhamentoChamadoResponse(
                chamado.getProtocolo(),
                chamado.getStatus(),
                chamado.getCreatedAt(),
                chamado.getUpdatedAt(),
                chamado.getRespostaEquipe());
        return ResponseEntity.ok(response);
    }
}
