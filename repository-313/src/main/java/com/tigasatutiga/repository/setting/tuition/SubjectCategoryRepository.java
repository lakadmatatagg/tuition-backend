package com.tigasatutiga.repository.setting.tuition;

import com.tigasatutiga.entities.setting.tuition.SubjectCategoryEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectCategoryRepository
        extends BaseRepository<SubjectCategoryEntity, Long> {
}
