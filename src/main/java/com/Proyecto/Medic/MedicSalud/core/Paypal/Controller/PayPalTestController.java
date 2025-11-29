package com.Proyecto.Medic.MedicSalud.core.Paypal.Controller;


import com.Proyecto.Medic.MedicSalud.core.Paypal.Service.PayPalService;
import com.Proyecto.Medic.MedicSalud.core.Paypal.dto.CrearOrdenResponse;
import com.Proyecto.Medic.MedicSalud.core.Paypal.dto.CrearPagoRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal-test")
@RequiredArgsConstructor
public class PayPalTestController {

    private final PayPalService payPalService;

    @PostMapping("/create")
    public ResponseEntity<CrearOrdenResponse> createOrder(@RequestBody CrearPagoRequest dto,
                                                          HttpServletRequest request) {

        String baseUrl = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort();

        String returnUrl = baseUrl + "/paypal-test.html";
        String cancelUrl = baseUrl + "/paypal-test.html?cancel=1";

        CrearOrdenResponse order = payPalService.crearOrden(dto, returnUrl, cancelUrl);

        return ResponseEntity.ok(order);
    }

    @PostMapping("/capture")
    public ResponseEntity<?> capture(@RequestParam   String orderId) {

        var result = payPalService.capturarOrden(orderId);

        return ResponseEntity.ok(result);
    }


}
