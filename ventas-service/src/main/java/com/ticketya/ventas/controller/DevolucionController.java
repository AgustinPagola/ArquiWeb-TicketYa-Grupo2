package com.ticketya.ventas.controller;

import com.ticketya.ventas.dto.DevolucionDTO;
import com.ticketya.ventas.dto.DevolucionRequest;
import com.ticketya.ventas.model.EstadoDevolucion;
import com.ticketya.ventas.service.DevolucionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devoluciones")
@RequiredArgsConstructor
public class DevolucionController {

    private final DevolucionService devolucionService;

    @PostMapping
    public ResponseEntity<DevolucionDTO> solicitar(@RequestHeader("X-Usuario-Id") Long usuarioId,
                                                   @Valid @RequestBody DevolucionRequest req) {
        // 201 CREATED
        // 403 si la entrada no es del usuario, 404 si no existe
        // 409 si faltan menos de 72 hs, la entrada no esta vendida o ya hay una solicitud pendiente
        return ResponseEntity.status(HttpStatus.CREATED).body(devolucionService.solicitar(usuarioId, req));
    }

    @GetMapping
    public ResponseEntity<List<DevolucionDTO>> listar(@RequestHeader(value = "X-Rol", required = false) String rol,
                                                      @RequestParam(defaultValue = "PENDIENTE") EstadoDevolucion estado) {
        // 200 OK, 403 si no es administrador
        Seguridad.exigirAdministrador(rol);
        return ResponseEntity.ok(devolucionService.listar(estado));
    }

    @PutMapping("/{id}/aprobacion")
    public ResponseEntity<DevolucionDTO> aprobar(@PathVariable Long id,
                                                 @RequestHeader(value = "X-Rol", required = false) String rol) {
        // 200 OK (se libera el lugar y se pide el reintegro), 403 si no es administrador
        // 404 si no existe, 409 si ya fue resuelta
        Seguridad.exigirAdministrador(rol);
        return ResponseEntity.ok(devolucionService.aprobar(id));
    }

    @PutMapping("/{id}/rechazo")
    public ResponseEntity<DevolucionDTO> rechazar(@PathVariable Long id,
                                                  @RequestHeader(value = "X-Rol", required = false) String rol) {
        // 200 OK, 403 si no es administrador, 404 si no existe, 409 si ya fue resuelta
        Seguridad.exigirAdministrador(rol);
        return ResponseEntity.ok(devolucionService.rechazar(id));
    }
}
