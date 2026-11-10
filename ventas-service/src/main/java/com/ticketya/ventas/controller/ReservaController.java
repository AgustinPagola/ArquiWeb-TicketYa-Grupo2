package com.ticketya.ventas.controller;

import com.ticketya.ventas.dto.CompraDTO;
import com.ticketya.ventas.dto.PagoRequest;
import com.ticketya.ventas.dto.ReservaDTO;
import com.ticketya.ventas.dto.ReservaRequest;
import com.ticketya.ventas.exception.BadRequestException;
import com.ticketya.ventas.model.EstadoCompra;
import com.ticketya.ventas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// El cliente se identifica con el header X-Usuario-Id (sin autenticacion en la entrega 1)
@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaDTO> reservar(@RequestHeader("X-Usuario-Id") Long usuarioId,
                                               @Valid @RequestBody ReservaRequest req) {
        // 201 CREATED
        // 400 si faltan datos o el asiento no existe
        // 404 si no existe el evento o el sector
        // 409 si el asiento esta tomado, no hay cupo, el evento no esta a la venta o se supera el limite por cliente
        // 503 si el servicio de catalogo no responde
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.reservar(usuarioId, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obtener(@PathVariable Long id,
                                              @RequestHeader("X-Usuario-Id") Long usuarioId) {
        // 200 OK, 403 si no es del usuario, 404 si no existe
        return ResponseEntity.ok(reservaService.obtener(usuarioId, id));
    }

    @PostMapping("/{id}/pago")
    public ResponseEntity<CompraDTO> pagar(@PathVariable Long id,
                                           @RequestHeader("X-Usuario-Id") Long usuarioId,
                                           @RequestHeader("Idempotency-Key") String claveIdempotencia,
                                           @Valid @RequestBody PagoRequest req) {
        // 200 OK (pago aprobado, entradas emitidas)
        // 402 PAYMENT REQUIRED (pago rechazado: los lugares se liberan)
        // 403 si la reserva no es del usuario, 404 si no existe
        // 409 si la reserva vencio o ya no esta pendiente, o hay otro pago en proceso
        if (claveIdempotencia.isBlank()) {
            throw new BadRequestException("El header Idempotency-Key no puede estar vacio");
        }
        CompraDTO compra = reservaService.pagar(usuarioId, id, claveIdempotencia, req);
        HttpStatus status = compra.estado() == EstadoCompra.APROBADA ? HttpStatus.OK : HttpStatus.PAYMENT_REQUIRED;
        return ResponseEntity.status(status).body(compra);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id,
                                         @RequestHeader("X-Usuario-Id") Long usuarioId) {
        // 204 NO CONTENT, 403 si no es del usuario, 404 si no existe, 409 si no esta pendiente
        reservaService.cancelar(usuarioId, id);
        return ResponseEntity.noContent().build();
    }
}
