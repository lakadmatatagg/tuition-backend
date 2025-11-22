package com.tigasatutiga.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BatchInvoiceResponseModel {

    private boolean success;
    private int totalProcessed;
    private int sentToTelegram;
    private int failedTelegram;
    private int addedToEmailBatch;
    private int emailBatchFailures;
}
