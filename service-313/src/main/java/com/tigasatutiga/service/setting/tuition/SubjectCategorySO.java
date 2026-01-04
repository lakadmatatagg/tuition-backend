package com.tigasatutiga.service.setting.tuition;

import com.tigasatutiga.entities.setting.tuition.SubjectCategoryEntity;
import com.tigasatutiga.models.setting.tuition.SubjectCategoryModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubjectCategorySO
        extends BaseSO<SubjectCategoryEntity, SubjectCategoryModel, Long> {
    Page<SubjectCategoryModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);
    List<SubjectCategoryModel> getList();
}

