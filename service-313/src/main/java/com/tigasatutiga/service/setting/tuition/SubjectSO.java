package com.tigasatutiga.service.setting.tuition;

import com.tigasatutiga.entities.setting.tuition.SubjectEntity;
import com.tigasatutiga.models.setting.tuition.SubjectCategoryModel;
import com.tigasatutiga.models.setting.tuition.SubjectModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubjectSO
        extends BaseSO<SubjectEntity, SubjectModel, Long> {
    Page<SubjectModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);

    List<SubjectModel> getListByGrades(List<SubjectCategoryModel> grades);
}
