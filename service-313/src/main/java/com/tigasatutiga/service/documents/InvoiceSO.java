package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

public interface InvoiceSO extends BaseSO<InvoiceEntity, InvoiceModel, Long> {
    Page<InvoiceModel> getAllInvoices(int pageNo, int pageSize, String sortField, String sortDir);
}
