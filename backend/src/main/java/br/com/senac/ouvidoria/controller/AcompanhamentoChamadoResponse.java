package br.com.senac.ouvidoria.controller;

import br.com.senac.ouvidoria.model.StatusChamado;
import java.time.Instant;

public record AcompanhamentoChamadoResponse(
        String protocol,
        StatusChamado status,
        Instant createdAt,
        Instant updatedAt,
        String teamResponse) {
}
