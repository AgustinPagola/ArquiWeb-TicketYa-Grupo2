package com.ticketya.catalogo.controller;

import com.ticketya.catalogo.dto.LugarDTO;
import com.ticketya.catalogo.dto.LugarRequest;
import com.ticketya.catalogo.service.LugarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lugares")
@RequiredArgsConstructor
public class LugarController {

    private final LugarService lugarService;

    @PostMapping
    public ResponseEntity<LugarDTO> crear(@Valid @RequestBody LugarRequest req) {
        // 201 CREATED, 400 si faltan datos o el sector es invalido
        return ResponseEntity.status(HttpStatus.CREATED).body(lugarService.crear(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LugarDTO> obtener(@PathVariable Long id) {
        // 200 OK, 404 si no existe
        return ResponseEntity.ok(lugarService.obtener(id));
    }

    @GetMapping
    public ResponseEntity<List<LugarDTO>> listar() {
        return ResponseEntity.ok(lugarService.listar());
    }
}
