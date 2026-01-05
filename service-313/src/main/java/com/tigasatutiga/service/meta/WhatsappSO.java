package com.tigasatutiga.service.meta;

import java.io.Serializable;

public interface WhatsappSO extends Serializable {

    void sendText(String to, String message);

    void sendTemplate(String to, String templateName, String languageCode);
}
