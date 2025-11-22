package com.tigasatutiga.service.documents;

import com.tigasatutiga.models.ApiResponseModel;
import com.tigasatutiga.models.BatchInvoiceResponseModel;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public interface InvoiceBatchSO extends Serializable {

    ResponseEntity<ApiResponseModel<BatchInvoiceResponseModel>> batchGenerateInvoice() throws Exception;
}
