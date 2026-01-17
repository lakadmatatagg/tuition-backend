package com.tigasatutiga.service.documents;

import com.tigasatutiga.models.ApiResponseModel;
import com.tigasatutiga.models.BatchInvoiceResponseModel;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public interface InvoiceBatchSO extends Serializable {

    ResponseEntity<ApiResponseModel<BatchInvoiceResponseModel>> batchGenerateInvoices() throws Exception;

    List<Long> getParentsWithoutInvoiceForCurrentMonth();
}
