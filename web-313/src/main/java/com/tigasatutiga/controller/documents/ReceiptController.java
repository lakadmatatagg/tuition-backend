package com.tigasatutiga.controller.documents;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.documents.ReceiptEntity;
import com.tigasatutiga.models.documents.ReceiptModel;
import com.tigasatutiga.service.documents.ReceiptSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/receipt")
public class ReceiptController extends BaseController<ReceiptEntity, ReceiptModel, Long> {

    @Autowired
    private ReceiptSO receiptSO;

    public ReceiptController(ReceiptSO service) {
        super(service);
    }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<ReceiptModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir) {
        return receiptSO.getAllReceipts(pageNo, pageSize, sortField, sortDir);
    }
}
