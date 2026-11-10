package com.ticketya.ventas.controller;

import com.ticketya.ventas.dto.EntradaDTO;
import com.ticketya.ventas.service.EntradaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entradas")
@RequiredArgsConstructor
public class EntradaController {

    private final EntradaService entradaService;

    @GetMapping
    public ResponseEntity<List<EntradaDTO>> misEntradas(@RequestHeader("X-Usuario-Id") Long usuarioId) {
        // 200 OK
        return ResponseEntity.ok(entradaService.listar(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaDTO> obtener(@PathVariable Long id,
                                              @RequestHeader("X-Usuario-Id") Long usuarioId) {
        // 200 OK, 403 si no es del usuario, 404 si no existe
        return ResponseEntity.ok(entradaService.obtener(usuarioId, id));
    }
}
