package com.ticketya.catalogo.controller;

import com.ticketya.catalogo.dto.*;
import com.ticketya.catalogo.exception.ForbiddenException;
import com.ticketya.catalogo.model.TipoEvento;
import com.ticketya.catalogo.service.CancelacionService;
import com.ticketya.catalogo.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;
    private final CancelacionService cancelacionService;

    @PostMapping
    public ResponseEntity<EventoDTO> crear(@Valid @RequestBody EventoRequest req) {
        // 201 CREATED (queda en BORRADOR), 404 si no existe el lugar, 400 si faltan datos
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.crear(req));
    }

    @GetMapping
    public ResponseEntity<List<EventoResumenDTO>> buscar(
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) String organizador,
            @RequestParam(required = false) String participante,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(defaultValue = "fecha") String orden) {
        // 200 OK, 400 si el orden o un filtro tienen formato invalido
        return ResponseEntity.ok(eventoService.buscar(tipo, organizador, participante, ciudad, desde, hasta, orden));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtener(@PathVariable Long id) {
        // 200 OK, 404 si no existe
        return ResponseEntity.ok(eventoService.obtener(id));
    }

    @PutMapping("/{id}/precios")
    public ResponseEntity<EventoDTO> configurarPrecios(@PathVariable Long id,
                                                        @Valid @RequestBody PreciosRequest req) {
        // 200 OK, 404 si no existe, 409 si no esta en BORRADOR, 400 si un sector no es del lugar
        return ResponseEntity.ok(eventoService.configurarPrecios(id, req));
    }

    @PostMapping("/{id}/publicacion")
    public ResponseEntity<EventoDTO> publicar(@PathVariable Long id) {
        // 200 OK, 404 si no existe, 409 si no esta en BORRADOR o faltan precios
        return ResponseEntity.ok(eventoService.publicar(id));
    }

    @PutMapping("/{id}/suspension")
    public ResponseEntity<EventoDTO> suspender(@PathVariable Long id,
                                               @RequestHeader(value = "X-Rol", required = false) String rol) {
        // 200 OK, 403 si no es administrador, 404 si no existe, 409 si no esta publicado
        exigirAdministrador(rol);
        return ResponseEntity.ok(eventoService.suspender(id));
    }

    @PutMapping("/{id}/cancelacion")
    public ResponseEntity<CancelacionDTO> cancelar(@PathVariable Long id,
                                                   @RequestHeader(value = "X-Rol", required = false) String rol,
                                                   @Valid @RequestBody CancelacionRequest req) {
        // 200 OK, 403 si no es administrador, 404 si no existe, 409 si ya estaba cancelado o finalizado
        exigirAdministrador(rol);
        return ResponseEntity.ok(cancelacionService.cancelar(id, req.motivo()));
    }

    // Sin autenticacion en esta entrega: el rol viaja en el header X-Rol (JWT llega en la entrega 2)
    private void exigirAdministrador(String rol) {
        if (!"ADMIN".equalsIgnoreCase(rol)) {
            throw new ForbiddenException("Esta accion requiere el header X-Rol: ADMIN");
        }
    }
}
