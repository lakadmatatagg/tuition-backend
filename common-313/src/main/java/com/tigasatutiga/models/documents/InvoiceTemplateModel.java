package com.tigasatutiga.models.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceTemplateModel {


    @JsonProperty("COMPANY_NAME")
    private String COMPANY_NAME;

    @JsonProperty("COMPANY_TAGLINE")
    private String COMPANY_TAGLINE;

    @JsonProperty("COMPANY_ADDRESS_1")
    private String COMPANY_ADDRESS_1;

    @JsonProperty("COMPANY_ADDRESS_2")
    private String COMPANY_ADDRESS_2;

    @JsonProperty("PHONE_LABEL")
    private String PHONE_LABEL;

    @JsonProperty("COMPANY_PHONE_NO")
    private String COMPANY_PHONE_NO;

    @JsonProperty("INVOICE_LABEL")
    private String INVOICE_LABEL;

    @JsonProperty("INVOICE_NO_LABEL")
    private String INVOICE_NO_LABEL;

    @JsonProperty("INVOICE_NO")
    private String INVOICE_NO;

    @JsonProperty("DATE_LABEL")
    private String DATE_LABEL;

    @JsonProperty("INVOICE_DATE")
    private String INVOICE_DATE;


    @JsonProperty("TO_LABEL")
    private String TO_LABEL;

    @JsonProperty("PARENT_NAME")
    private String PARENT_NAME;

    @JsonProperty("PARENT_PHONE_NO")
    private String PARENT_PHONE_NO;


    @JsonProperty("ITEM_LABEL")
    private String ITEM_LABEL;

    @JsonProperty("SUBJECT_LABEL")
    private String SUBJECT_LABEL;

    @JsonProperty("GRADE_LABEL")
    private String GRADE_LABEL;

    @JsonProperty("AMOUNT_LABEL")
    private String AMOUNT_LABEL;


    @JsonProperty("invoiceItems")
    private List<InvoiceItemModel> invoiceItems;


    @JsonProperty("TOTAL_LABEL")
    private String TOTAL_LABEL;

    @JsonProperty("SUB_TOTAL")
    private BigDecimal SUB_TOTAL;

    @JsonProperty("PAYMENT_REMARKS")
    private String PAYMENT_REMARKS;

    @JsonProperty("PAYMENT_ACCOUNT_INFO")
    private String PAYMENT_ACCOUNT_INFO;

    @JsonProperty("END_LABEL")
    private String END_LABEL;
}
