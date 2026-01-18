package com.tigasatutiga.controller.documents;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.models.ApiResponseModel;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTableModel;
import com.tigasatutiga.models.documents.InvoiceTemplateModel;
import com.tigasatutiga.service.documents.InvoiceBatchSO;
import com.tigasatutiga.service.documents.InvoiceSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/invoice")
public class InvoiceController extends BaseController<InvoiceEntity, InvoiceModel, Long> {

    @Autowired
    private InvoiceSO invoiceSO;

    @Autowired
    private InvoiceBatchSO invoiceBatchSO;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job generateMonthlyInvoicesJob; // from InvoiceBatchConfig

    public InvoiceController(InvoiceSO service) { super(service); }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}/{billingMonth}")
    public Page<InvoiceTableModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir, @PathVariable LocalDate billingMonth) {
        return invoiceSO.getAllInvoices(pageNo, pageSize, sortField, sortDir, billingMonth);
    }

    @GetMapping("/view/{parentId}/{billingMonth}")
    public ResponseEntity<ApiResponseModel<InvoiceTemplateModel>> viewInvoice(@PathVariable Long parentId, @PathVariable LocalDate billingMonth) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseModel.success(invoiceSO.viewInvoice(parentId, billingMonth), "Invoice retrieved successfully"));
    }

    @PostMapping("save-full-invoice")
    public ResponseEntity<ApiResponseModel<InvoiceModel>> createInvoiceWithItems(@RequestBody InvoiceModel model) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseModel.success(invoiceSO.createInvoiceWithItems(model), "Invoice created successfully"));
    }

    @GetMapping("/save/{parentId}")
    public ResponseEntity<ApiResponseModel<InvoiceTemplateModel>> save(@PathVariable Long parentId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseModel.success(invoiceSO.generateInvoiceForParent(parentId), "Invoice created successfully"));
    }

    @GetMapping("/batch/generate-current-month")
    public ResponseEntity<ApiResponseModel<String>> generateCurrentMonthInvoices() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // unique parameter
                    .toJobParameters();

            jobLauncher.run(generateMonthlyInvoicesJob, jobParameters);

            return ResponseEntity.ok(ApiResponseModel.success(
                    "Batch job started successfully",
                    "Batch job triggered"
            ));
        } catch (Exception e) {
            log.error("Failed to start batch job", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseModel.failure(
                            "Failed to start batch job: " + e.getMessage()
                    ));
        }
    }

    @GetMapping("test")
    public List<Long> test() {
        return invoiceBatchSO.getParentsWithoutInvoiceForCurrentMonth();
    }


}
