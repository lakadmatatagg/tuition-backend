package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.ReceiptEntity;
import com.tigasatutiga.models.documents.ReceiptModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

public interface ReceiptSO extends BaseSO<ReceiptEntity, ReceiptModel, Long> {
    Page<ReceiptModel> getAllReceipts(int pageNo, int pageSize, String sortField, String sortDir);
}
