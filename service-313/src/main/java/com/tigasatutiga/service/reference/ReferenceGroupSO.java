package com.tigasatutiga.service.reference;

import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceGroupEntity;
import com.tigasatutiga.models.config.reference.ReferenceGroupModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReferenceGroupSO extends BaseSO<ReferenceGroupEntity, ReferenceGroupModel, Long> {

    Page<ReferenceGroupModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);

    List<ReferenceGroupModel> getList();
}
