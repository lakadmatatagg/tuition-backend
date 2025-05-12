package com.tigasatutiga.repository.student;

import com.tigasatutiga.entities.tuitionez.student.StudentEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends BaseRepository<StudentEntity, Long> {

    @Query("SELECT s FROM StudentEntity s WHERE s.parent.id = ?1")
    List<StudentEntity> findByParentId(Long email);
}
