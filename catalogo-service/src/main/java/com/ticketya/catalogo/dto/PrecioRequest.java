package com.ticketya.catalogo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PrecioRequest(
        @NotNull Long sectorId,
        @NotNull @Positive BigDecimal precio
) {
}
