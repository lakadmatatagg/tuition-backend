package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.models.documents.InvoiceItemModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

public interface InvoiceItemSO extends BaseSO<InvoiceItemEntity, InvoiceItemModel, Long> {
    Page<InvoiceItemModel> getAllInvoiceItems(int pageNo, int pageSize, String sortField, String sortDir);
}
