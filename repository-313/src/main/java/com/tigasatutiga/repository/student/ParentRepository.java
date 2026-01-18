package com.tigasatutiga.repository.student;

import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ParentRepository extends BaseRepository<ParentEntity, Long> {
    ParentEntity findByPhoneNoEquals(String phone);

    ParentEntity findByTelegramChatIdEquals(String telegramId);

    @Query("""
    select p.id
    from ParentEntity p
    where not exists (
        select 1
        from InvoiceEntity i
        where i.parent.id = p.id
          and i.billingMonth = :billingMonth
    )
""")
    List<Long> findParentIdsWithoutInvoice(@Param("billingMonth") LocalDate billingMonth);
}
