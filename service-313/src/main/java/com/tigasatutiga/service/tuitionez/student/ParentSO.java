package com.tigasatutiga.service.tuitionez.student;

import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.models.tuitionez.student.ParentModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ParentSO extends BaseSO<ParentEntity, ParentModel, Long> {

    Page<ParentModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);

    List<ParentModel> getAll();
}
