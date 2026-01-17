package com.tigasatutiga.models.documents;

import com.tigasatutiga.models.student.ParentModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class InvoiceBuildResult {
    private ParentModel parent;
    private List<InvoiceItemModel> items;
    private BigDecimal totalAmount;
}

