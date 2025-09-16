package com.tigasatutiga.controller.documents;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTableModel;
import com.tigasatutiga.models.student.ParentModel;
import com.tigasatutiga.service.documents.InvoiceSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping("/invoice")
public class InvoiceController extends BaseController<InvoiceEntity, InvoiceModel, Long> {

    @Autowired
    private InvoiceSO invoiceSO;

    public InvoiceController(InvoiceSO service) { super(service); }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}/{billingMonth}")
    public Page<InvoiceTableModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir, @PathVariable LocalDate billingMonth) {
        return invoiceSO.getAllInvoices(pageNo, pageSize, sortField, sortDir, billingMonth);
    }

    @PostMapping("save-full-invoice")
    public int createInvoiceWithItems(@RequestBody InvoiceModel model) {
        return invoiceSO.createInvoiceWithItems(model);
    }
}
