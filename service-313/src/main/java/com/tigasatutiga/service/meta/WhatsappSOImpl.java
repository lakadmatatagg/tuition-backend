package com.tigasatutiga.service.meta;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class WhatsappSOImpl implements WhatsappSO {

    @Value("${whatsapp.enabled:false}")
    private boolean enabled;

    @Value("${whatsapp.base-url:}")
    private String baseUrl;

    @Value("${whatsapp.phone-number-id:}")
    private String phoneNumberId;

    @Value("${whatsapp.token:}")
    private String token;

    private final RestClient restClient = RestClient.create();

    @Override
    public void sendText(String to, String message) {

        if (!enabled) {
            return;
        }

        String url = baseUrl + "/" + phoneNumberId + "/messages";

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", to,
                "type", "text",
                "text", Map.of(
                        "body", message
                )
        );

        restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void sendTemplate(String to, String templateName, String languageCode) {

        if (!enabled) {
            return;
        }

        String url = baseUrl + "/" + phoneNumberId + "/messages";

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", to,
                "type", "template",
                "template", Map.of(
                        "name", templateName,
                        "language", Map.of(
                                "code", languageCode
                        )
                )
        );

        restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }

}
