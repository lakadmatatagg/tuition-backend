package com.tigasatutiga.repository.documents;

import com.tigasatutiga.entities.documents.ReceiptEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends BaseRepository<ReceiptEntity, Long> {
}