package com.tigasatutiga.models.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceTableModel {
    private Long id;
    private String name;
    private String phoneNo;
    private String telegramChatId;
    private List<InvoiceModel> invoices;
}
