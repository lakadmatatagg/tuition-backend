package com.tigasatutiga.repository.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends BaseRepository<InvoiceEntity, Long> {
}