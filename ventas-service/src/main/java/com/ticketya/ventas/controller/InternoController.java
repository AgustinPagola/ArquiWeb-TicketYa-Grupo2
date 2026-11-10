package com.ticketya.ventas.controller;

import com.ticketya.ventas.dto.CancelacionEventoDTO;
import com.ticketya.ventas.service.CancelacionEventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Endpoints que usa SOLO el servicio de catalogo. El gateway no los expone hacia afuera.
@RestController
@RequestMapping("/interno")
@RequiredArgsConstructor
public class InternoController {

    private final CancelacionEventoService cancelacionEventoService;

    @PostMapping("/eventos/{eventoId}/cancelacion")
    public ResponseEntity<CancelacionEventoDTO> cancelarEvento(@PathVariable Long eventoId) {
        // 200 OK: las entradas vendidas pasan a A_REINTEGRAR (idempotente)
        return ResponseEntity.ok(cancelacionEventoService.cancelarEvento(eventoId));
    }
}
