package com.ticketya.ventas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReservaRequest(
        @NotNull Long eventoId,
        @NotNull Long sectorId,
        @Valid List<AsientoRequest> asientos,   // sectores NUMERADO
        Integer cantidad                         // sectores NO_NUMERADO
) {
}
