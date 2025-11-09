package com.tigasatutiga.service.reference;

import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceCodeEntity;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReferenceCodeSO extends BaseSO<ReferenceCodeEntity, ReferenceCodeModel, Long> {

    Page<ReferenceCodeModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);

    List<ReferenceCodeModel> getListByGroup(String groupCode);
}
