package com.tigasatutiga.repository.student;

import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ParentRepository extends BaseRepository<ParentEntity, Long> {
    ParentEntity findByPhoneNoEquals(String phone);

    ParentEntity findByTelegramChatIdEquals(String telegramId);

    @Query("SELECT A FROM ParentEntity A LEFT JOIN InvoiceEntity B ON A.id = B.parent.id AND B.billingMonth = ?1")
    List<ParentEntity> findAllWithInvoice(Date billingMonth);
}
