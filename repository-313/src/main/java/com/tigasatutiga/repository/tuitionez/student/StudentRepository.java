package com.tigasatutiga.repository.tuitionez.student;

import com.tigasatutiga.entities.tuitionez.student.StudentEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<StudentEntity, Long> {
}
