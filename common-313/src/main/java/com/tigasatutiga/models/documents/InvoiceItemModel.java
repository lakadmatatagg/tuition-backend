package com.tigasatutiga.models.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.models.student.StudentModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceItemModel {
    private Long id;
    private InvoiceModel invoice;
    private StudentModel student;
    private ReferenceCodeModel subject;
    private String description;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
