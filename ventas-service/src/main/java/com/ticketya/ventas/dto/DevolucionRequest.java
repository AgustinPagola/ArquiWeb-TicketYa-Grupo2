package com.ticketya.ventas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DevolucionRequest(@NotNull Long entradaId, @NotBlank String motivo) {
}
