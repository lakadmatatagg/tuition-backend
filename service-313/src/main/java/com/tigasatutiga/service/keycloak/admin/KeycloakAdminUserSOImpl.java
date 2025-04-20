package com.tigasatutiga.service.keycloak.admin;

import com.tigasatutiga.service.keycloak.KeycloakAdminTokenSOImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class KeycloakAdminUserSOImpl implements KeycloakAdminUserSO {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KeycloakAdminTokenSOImpl keycloakAdminTokenSO;

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakAdminUserSOImpl(KeycloakAdminTokenSOImpl keycloakAdminTokenSO) {
        this.keycloakAdminTokenSO = keycloakAdminTokenSO;
    }

    public Map getUserByUsername(String username) {
        String url = keycloakUrl + "/admin/realms/" + realm + "/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakAdminTokenSO.getAdminToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        List<?> users = response.getBody();
        if (!users.isEmpty()) {
            return (Map) users.getFirst();
        }

        throw new RuntimeException("User not found");
    }

    public void updateUserProfile(String userId, Map<String, Object> updates) {
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakAdminTokenSO.getAdminToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(updates, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    public void updateUserPassword(String userId, String newPassword) {
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakAdminTokenSO.getAdminToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "type", "password",
                "temporary", false,
                "value", newPassword
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }
}
