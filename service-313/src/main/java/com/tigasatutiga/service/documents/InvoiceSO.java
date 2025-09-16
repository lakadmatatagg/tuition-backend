package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTableModel;
import com.tigasatutiga.models.student.ParentModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface InvoiceSO extends BaseSO<InvoiceEntity, InvoiceModel, Long> {
    Page<InvoiceTableModel> getAllInvoices(int pageNo, int pageSize, String sortField, String sortDir, LocalDate billingMonth);

    int createInvoiceWithItems(InvoiceModel model);
}
