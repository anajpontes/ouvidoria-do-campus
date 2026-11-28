package br.com.senac.ouvidoria.controller;

import br.com.senac.ouvidoria.model.StatusChamado;
import java.time.Instant;

public record ChamadoCriadoResponse(
        String protocol,
        StatusChamado status,
        Instant createdAt) {
}
