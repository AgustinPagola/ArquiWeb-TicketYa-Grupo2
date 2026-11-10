package com.ticketya.catalogo.dto;

import com.ticketya.catalogo.model.TipoEvento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record EventoRequest(
        @NotBlank String nombre,
        String descripcion,
        @NotNull TipoEvento tipo,
        @NotBlank String organizador,
        List<String> participantes,
        @NotNull @Future LocalDateTime fechaHora,
        @NotNull Long lugarId
) {
}
