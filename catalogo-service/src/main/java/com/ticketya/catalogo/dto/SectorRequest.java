package com.ticketya.catalogo.dto;

import com.ticketya.catalogo.model.TipoSector;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SectorRequest(
        @NotBlank String nombre,
        @NotNull TipoSector tipo,
        Integer capacidad,        // NO_NUMERADO
        Integer filas,            // NUMERADO
        Integer asientosPorFila   // NUMERADO
) {
}
