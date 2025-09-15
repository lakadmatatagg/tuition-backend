package com.tigasatutiga.controller.documents;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.service.documents.InvoiceSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/invoice")
public class InvoiceController extends BaseController<InvoiceEntity, InvoiceModel, Long> {

    @Autowired
    private InvoiceSO invoiceSO;

    public InvoiceController(InvoiceSO service) { super(service); }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<InvoiceModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir) {
        return invoiceSO.getAllInvoices(pageNo, pageSize, sortField, sortDir);
    }
}
