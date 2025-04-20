package com.tigasatutiga.service.keycloak.admin;

import java.util.Map;

public interface KeycloakAdminUserSO {

    Map getUserByUsername(String username);

    void updateUserProfile(String userId, Map<String, Object> updates);

    void updateUserPassword(String userId, String newPassword);
}
