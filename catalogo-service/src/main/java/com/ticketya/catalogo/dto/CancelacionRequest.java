package com.ticketya.catalogo.dto;

import jakarta.validation.constraints.NotBlank;

public record CancelacionRequest(@NotBlank String motivo) {
}
