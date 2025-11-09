package com.tigasatutiga.repository.reference;

import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceCodeEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceCodeRepository extends BaseRepository<ReferenceCodeEntity, Long> {

    @Query("SELECT r FROM ReferenceCodeEntity r WHERE r.group.code = :groupCode ORDER BY r.order")
    List<ReferenceCodeEntity> findByGroupCode(String groupCode);
}
