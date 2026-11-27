package br.com.senac.ouvidoria.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CriarChamadoRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank String location,
        @NotBlank @Email String email) {
}
