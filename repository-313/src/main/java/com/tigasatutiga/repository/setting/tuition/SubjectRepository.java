package com.tigasatutiga.repository.setting.tuition;

import com.tigasatutiga.entities.setting.tuition.SubjectCategoryEntity;
import com.tigasatutiga.entities.setting.tuition.SubjectEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository
        extends BaseRepository<SubjectEntity, Long> {

    @Query("SELECT s FROM SubjectEntity s JOIN s.category g WHERE g IN :grades ORDER BY s.category.categoryName ASC")
    List<SubjectEntity> findByGrades(List<SubjectCategoryEntity> grades);
}
