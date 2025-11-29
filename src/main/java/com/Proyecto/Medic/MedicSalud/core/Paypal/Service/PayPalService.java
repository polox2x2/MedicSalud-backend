package com.Proyecto.Medic.MedicSalud.core.Paypal.Service;

import com.Proyecto.Medic.MedicSalud.core.Paypal.Model.Paypal;
import com.Proyecto.Medic.MedicSalud.core.Paypal.dto.CrearOrdenResponse;
import com.Proyecto.Medic.MedicSalud.core.Paypal.dto.CrearPagoRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PayPalService {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String paypalMode;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getBaseUrl() {
        return "live".equalsIgnoreCase(paypalMode)
                ? "https://api-m.paypal.com"
                : "https://api-m.sandbox.paypal.com";
    }

    private String obtenerAccessToken() {
        String basicAuth = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + basicAuth);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                getBaseUrl() + "/v1/oauth2/token",
                requestEntity,
                JsonNode.class
        );

        if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null
                && response.getBody().has("access_token")) {
            return response.getBody().get("access_token").asText();
        }
        throw new RuntimeException("No se pudo obtener el access_token de PayPal");
    }

    public CrearOrdenResponse crearOrden(CrearPagoRequest dto,
                                         String returnUrl,
                                         String cancelUrl) {

        String accessToken = obtenerAccessToken();

        ObjectNode body = JsonNodeFactory.instance.objectNode();
        body.put("intent", "CAPTURE");

        ArrayNode purchaseUnits = body.putArray("purchase_units");
        ObjectNode unit = purchaseUnits.addObject();
        ObjectNode amount = unit.putObject("amount");
        amount.put("currency_code", dto.getMoneda());
        amount.put("value", dto.getMonto().toPlainString());

        ObjectNode appContext = body.putObject("application_context");
        appContext.put("return_url", returnUrl);
        appContext.put("cancel_url", cancelUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                getBaseUrl() + "/v2/checkout/orders",
                requestEntity,
                JsonNode.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Error al crear orden en PayPal");
        }

        JsonNode order = response.getBody();
        String orderId = order.get("id").asText();

        String approveLink = null;
        if (order.has("links") && order.get("links").isArray()) {
            for (JsonNode link : order.get("links")) {
                if ("approve".equals(link.get("rel").asText())) {
                    approveLink = link.get("href").asText();
                    break;
                }
            }
        }

        Paypal pago = Paypal.builder()
                .paypalOrderId(orderId)
                .monto(dto.getMonto())
                .moneda(dto.getMoneda())
                .estado("CREATED")
                .referenciaId(dto.getReferenciaId())
                .referenciaTipo(dto.getReferenciaTipo())
                .fechaCreacion(LocalDateTime.now())
                .build();


        return CrearOrdenResponse.builder()
                .paypalOrderId(orderId)
                .approveLink(approveLink)
                .status("CREATED")
                .build();
    }

    public JsonNode capturarOrden(String orderId) {

        String accessToken = obtenerAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                getBaseUrl() + "/v2/checkout/orders/" + orderId + "/capture",
                requestEntity,
                JsonNode.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Error al capturar la orden en PayPal");
        }

        JsonNode capture = response.getBody();
        String status = capture.get("status").asText(); // COMPLETED, etc.


        return capture;
    }




}
