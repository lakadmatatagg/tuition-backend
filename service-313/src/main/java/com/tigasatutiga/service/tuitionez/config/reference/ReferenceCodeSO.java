package com.tigasatutiga.service.tuitionez.config.reference;

import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceCodeEntity;
import com.tigasatutiga.models.tuitionez.config.reference.ReferenceCodeModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

public interface ReferenceCodeSO extends BaseSO<ReferenceCodeEntity, ReferenceCodeModel, Long> {
    Page<ReferenceCodeModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);
}
