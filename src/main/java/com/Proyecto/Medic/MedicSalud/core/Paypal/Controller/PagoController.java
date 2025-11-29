package com.Proyecto.Medic.MedicSalud.core.Paypal.Controller;

import com.Proyecto.Medic.MedicSalud.core.Paypal.Service.PayPalService;
import com.Proyecto.Medic.MedicSalud.core.Paypal.dto.CrearOrdenResponse;
import com.Proyecto.Medic.MedicSalud.core.Paypal.dto.CrearPagoRequest;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
public class PagoController {


    private final PayPalService pagoService;
    @PostMapping("/create-order")
    public ResponseEntity<CrearOrdenResponse> createOrder(@RequestBody CrearPagoRequest dto,
                                                          HttpServletRequest request) {

        String baseUrl = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort();

        String returnUrl = baseUrl + "/api/paypal/return";
        String cancelUrl = baseUrl + "/api/paypal/cancel";

        CrearOrdenResponse order = pagoService.crearOrden(dto, returnUrl, cancelUrl);

        return ResponseEntity.ok(order);
    }

    @PostMapping("/capture")
    public ResponseEntity<JsonNode> captureOrder(@RequestParam("orderId") String orderId) {
        JsonNode capture = pagoService.capturarOrden(orderId);
        return ResponseEntity.ok(capture);
    }

    @GetMapping("/return")
    public ResponseEntity<String> handleReturn(@RequestParam("token") String orderId) {
        return ResponseEntity.ok("Pago aprobado. token = " + orderId);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> handleCancel() {
        return ResponseEntity.ok("Pago cancelado por el usuario.");
    }


}
