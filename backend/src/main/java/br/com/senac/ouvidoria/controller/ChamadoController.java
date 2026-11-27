package br.com.senac.ouvidoria.controller;

import br.com.senac.ouvidoria.model.Chamado;
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

    public ChamadoController(RegistrarChamadoService registrarChamadoService) {
        this.registrarChamadoService = registrarChamadoService;
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
}
