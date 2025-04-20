package com.tigasatutiga.service.keycloak;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.time.Instant;
import java.util.Map;

@Service
public class KeycloakAdminTokenSOImpl implements KeycloakAdminTokenSO {

    private final RestTemplate restTemplate = new RestTemplate();
    private String accessToken;
    private Instant expiresAt;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    @Value("${keycloak.url}")
    private String keycloakUrl;

    public synchronized String getAdminToken() {
        if (accessToken == null || Instant.now().isAfter(expiresAt)) {
            fetchNewToken();
        }
        return accessToken;
    }

    public void fetchNewToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        String tokenEndpoint = keycloakUrl + "/realms/master/protocol/openid-connect/token";

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

        Map<String, Object> responseBody = response.getBody();
        this.accessToken = (String) responseBody.get("access_token");
        int expiresIn = (int) responseBody.get("expires_in");

        this.expiresAt = Instant.now().plusSeconds(expiresIn - 30); // buffer 30s
    }
}
