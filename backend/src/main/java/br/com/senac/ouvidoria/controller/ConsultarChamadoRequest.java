package br.com.senac.ouvidoria.controller;

import jakarta.validation.constraints.NotBlank;

public record ConsultarChamadoRequest(
        @NotBlank String protocol,
        @NotBlank String accessCode) {
}
