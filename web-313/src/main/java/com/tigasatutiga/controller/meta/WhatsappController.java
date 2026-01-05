package com.tigasatutiga.controller.meta;

import com.tigasatutiga.service.meta.WhatsappSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whatsapp")
public class WhatsappController {

    @Autowired
    private WhatsappSO whatsAppSO;

    @PostMapping("/send")
    public ResponseEntity<Void> send(
            @RequestParam String to,
            @RequestParam String message
    ) {
        whatsAppSO.sendText(to, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-template")
    public ResponseEntity<Void> sendTemplate(
            @RequestParam String to,
            @RequestParam String templateName,
            @RequestParam(defaultValue = "en_US") String language
    ) {
        whatsAppSO.sendTemplate(to, templateName, language);
        return ResponseEntity.ok().build();
    }
}
