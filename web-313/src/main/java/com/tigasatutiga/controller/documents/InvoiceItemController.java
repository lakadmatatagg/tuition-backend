package com.tigasatutiga.controller.documents;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.models.documents.InvoiceItemModel;
import com.tigasatutiga.service.documents.InvoiceItemSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/invoice-item")
public class InvoiceItemController extends BaseController<InvoiceItemEntity, InvoiceItemModel, Long> {

    @Autowired
    private InvoiceItemSO invoiceItemSO;

    public InvoiceItemController(InvoiceItemSO service) {
        super(service);
    }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<InvoiceItemModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize,
                                          @PathVariable String sortField, @PathVariable String sortDir) {
        return invoiceItemSO.getAllInvoiceItems(pageNo, pageSize, sortField, sortDir);
    }
}
