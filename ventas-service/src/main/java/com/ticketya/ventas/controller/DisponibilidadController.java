package com.ticketya.ventas.controller;

import com.ticketya.ventas.dto.DisponibilidadDTO;
import com.ticketya.ventas.service.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disponibilidad")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping("/eventos/{eventoId}/sectores/{sectorId}")
    public ResponseEntity<DisponibilidadDTO> consultar(@PathVariable Long eventoId,
                                                       @PathVariable Long sectorId) {
        // 200 OK, 404 si no existe el evento o el sector, 503 si catalogo no responde
        return ResponseEntity.ok(disponibilidadService.consultar(eventoId, sectorId));
    }
}
