package com.tigasatutiga.repository.student;

import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends BaseRepository<ParentEntity, Long> {
    ParentEntity findByPhoneNoEquals(String phone);
}
