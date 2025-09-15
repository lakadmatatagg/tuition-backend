package com.tigasatutiga.models.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiptModel {
    private Long id;
    private String receiptNo;
    private InvoiceModel invoice;
    private LocalDate paymentDate;
    private BigDecimal amountPaid;
    private String paymentMethod;
    private String status;  // VALID / REFUNDED
    private LocalDate createdAt;
    private LocalDate updatedAt;
}