package com.ticketya.ventas.dto;

import jakarta.validation.constraints.NotBlank;

public record PagoRequest(@NotBlank String medioPago) {
}
