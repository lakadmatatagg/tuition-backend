package com.tigasatutiga.service.tuitionez.student;

import com.tigasatutiga.entities.tuitionez.student.StudentEntity;
import com.tigasatutiga.models.tuitionez.student.StudentModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

public interface StudentSO extends BaseSO<StudentEntity, StudentModel, Long> {

    Page<StudentModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);
}
