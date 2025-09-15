package com.tigasatutiga.models.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigasatutiga.models.student.ParentModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceModel {
    private Long id;
    private String invoiceNo;
    private ParentModel parent;
    private LocalDate issueDate;
    private LocalDate billingMonth;
    private BigDecimal totalAmount;
    private Boolean isCancelled;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
