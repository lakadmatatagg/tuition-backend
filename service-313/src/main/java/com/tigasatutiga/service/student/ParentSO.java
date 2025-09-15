package com.tigasatutiga.service.student;

import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.models.student.ParentModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ParentSO extends BaseSO<ParentEntity, ParentModel, Long> {

    Page<ParentModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);

    List<ParentModel> getAll();

    ParentModel getByPhone(String phone);

    ParentModel getByTelegram(String telegramId);
}
