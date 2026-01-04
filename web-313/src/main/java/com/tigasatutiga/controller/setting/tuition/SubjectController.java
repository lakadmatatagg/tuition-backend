package com.tigasatutiga.controller.setting.tuition;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.setting.tuition.SubjectEntity;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.models.setting.tuition.SubjectCategoryModel;
import com.tigasatutiga.models.setting.tuition.SubjectModel;
import com.tigasatutiga.service.setting.tuition.SubjectSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setting/tuition/subject")
public class SubjectController
        extends BaseController<SubjectEntity, SubjectModel, Long> {

    @Autowired
    private SubjectSO service;

    public SubjectController(SubjectSO service) {
        super(service);
    }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<SubjectModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir) {
        return service.getAll(pageNo, pageSize, sortField, sortDir);
    }

    @PostMapping("/list-by-grades")
    public List<SubjectModel> getListByGrades(@RequestBody List<SubjectCategoryModel> gradeList) {
        return service.getListByGrades(gradeList);
    }
}
