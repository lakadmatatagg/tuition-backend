package com.tigasatutiga.service.student;

import com.tigasatutiga.entities.tuitionez.student.StudentEntity;
import com.tigasatutiga.models.student.StudentModel;
import com.tigasatutiga.service.BaseSO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentSO extends BaseSO<StudentEntity, StudentModel, Long> {

    Page<StudentModel> getAll(int pageNo, int pageSize, String sortField, String sortDir);

    List<StudentModel> getListByParentId(Long parentId);

    List<StudentModel> getListByTelegramId(String telegramId);
}
