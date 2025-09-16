package com.tigasatutiga.repository.documents;

import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends BaseRepository<InvoiceItemEntity, Long> {
}
