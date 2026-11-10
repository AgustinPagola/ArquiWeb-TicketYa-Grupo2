package com.ticketya.ventas.dto;

import jakarta.validation.constraints.NotNull;

public record AsientoRequest(@NotNull Integer fila, @NotNull Integer numero) {
}
