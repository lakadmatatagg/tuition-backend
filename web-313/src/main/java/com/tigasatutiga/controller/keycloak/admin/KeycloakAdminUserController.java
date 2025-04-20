package com.tigasatutiga.controller.keycloak.admin;

import com.tigasatutiga.service.keycloak.admin.KeycloakAdminUserSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/keycloak/user")
public class KeycloakAdminUserController {
    private final KeycloakAdminUserSO keycloakAdminUserSO;

    public KeycloakAdminUserController(KeycloakAdminUserSO keycloakAdminUserSO) {
        this.keycloakAdminUserSO = keycloakAdminUserSO;
    }

    // 🔹 Get user profile by username
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Map user = keycloakAdminUserSO.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    // 🔹 Update user profile by userId
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String userId, @RequestBody Map<String, Object> updates) {
        keycloakAdminUserSO.updateUserProfile(userId, updates);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Update password by userId
    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable String userId, @RequestBody Map<String, Object> request) {
        String newPassword = (String) request.get("newPassword");
        keycloakAdminUserSO.updateUserPassword(userId, newPassword);
        return ResponseEntity.noContent().build();
    }
}
