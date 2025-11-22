package com.tigasatutiga.controller.documents;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.models.ApiResponseModel;
import com.tigasatutiga.models.BatchInvoiceResponseModel;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTableModel;
import com.tigasatutiga.service.documents.InvoiceBatchSO;
import com.tigasatutiga.service.documents.InvoiceSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping("/invoice")
public class InvoiceController extends BaseController<InvoiceEntity, InvoiceModel, Long> {

    @Autowired
    private InvoiceSO invoiceSO;

    @Autowired
    private InvoiceBatchSO invoiceBatchSO;

    public InvoiceController(InvoiceSO service) { super(service); }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}/{billingMonth}")
    public Page<InvoiceTableModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir, @PathVariable LocalDate billingMonth) {
        return invoiceSO.getAllInvoices(pageNo, pageSize, sortField, sortDir, billingMonth);
    }

    @PostMapping("save-full-invoice")
    public ResponseEntity<ApiResponseModel<InvoiceModel>> createInvoiceWithItems(@RequestBody InvoiceModel model) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseModel.success(invoiceSO.createInvoiceWithItems(model), "Invoice created successfully"));
    }

    @GetMapping("/batch/generate-current-month")
    public ResponseEntity<ApiResponseModel<BatchInvoiceResponseModel>> generateCurrentMonthInvoices() throws Exception {
        return invoiceBatchSO.batchGenerateInvoice();
    }

}
