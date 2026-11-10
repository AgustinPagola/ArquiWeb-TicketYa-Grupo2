package com.ticketya.catalogo.controller;

import com.ticketya.catalogo.dto.SectorVentaDTO;
import com.ticketya.catalogo.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Endpoints que usa SOLO el servicio de ventas. El gateway no los expone hacia afuera.
@RestController
@RequestMapping("/interno")
@RequiredArgsConstructor
public class InternoController {

    private final EventoService eventoService;

    @GetMapping("/eventos/{eventoId}/sectores/{sectorId}")
    public ResponseEntity<SectorVentaDTO> sectorParaVenta(@PathVariable Long eventoId,
                                                          @PathVariable Long sectorId) {
        // 200 OK, 404 si el sector no tiene precio en ese evento
        return ResponseEntity.ok(eventoService.obtenerSectorVenta(eventoId, sectorId));
    }
}
