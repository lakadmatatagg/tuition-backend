package com.tigasatutiga.controller.tuitionez.student;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.tuitionez.student.StudentEntity;
import com.tigasatutiga.models.tuitionez.student.StudentModel;
import com.tigasatutiga.service.tuitionez.student.StudentSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController extends BaseController<StudentEntity, StudentModel, Long> {

    @Autowired
    private StudentSO studentSO;

    private StudentController(StudentSO service) { super(service); }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<StudentModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir) {
        return studentSO.getAll(pageNo, pageSize, sortField, sortDir);
    }
}
