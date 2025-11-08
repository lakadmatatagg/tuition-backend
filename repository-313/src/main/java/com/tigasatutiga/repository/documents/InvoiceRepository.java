package com.tigasatutiga.repository.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends BaseRepository<InvoiceEntity, Long> {

    @Query("SELECT i FROM InvoiceEntity i WHERE i.parent.id IN (:parentIds) AND i.billingMonth = :billingMonth")
    List<InvoiceEntity> findByParentIdInAndBillingMonth(List<Long> parentIds, LocalDate billingMonth);

    @Query("SELECT A FROM InvoiceEntity A WHERE A.parent.id = :parentId AND A.billingMonth = :billingMonth")
    Optional<InvoiceEntity> findByParentIdAndBillingMonth(Long parentId, LocalDate billingMonth);
}