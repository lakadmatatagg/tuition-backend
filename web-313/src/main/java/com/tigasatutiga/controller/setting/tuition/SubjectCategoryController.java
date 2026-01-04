package com.tigasatutiga.controller.setting.tuition;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.setting.tuition.SubjectCategoryEntity;
import com.tigasatutiga.models.setting.tuition.SubjectCategoryModel;
import com.tigasatutiga.models.setting.tuition.SubjectModel;
import com.tigasatutiga.service.setting.tuition.SubjectCategorySO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setting/tuition/subject-category")
public class SubjectCategoryController
        extends BaseController<SubjectCategoryEntity, SubjectCategoryModel, Long> {

    @Autowired
    private SubjectCategorySO service;

    public SubjectCategoryController(SubjectCategorySO service) {
        super(service);
    }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<SubjectCategoryModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir) {
        return service.getAll(pageNo, pageSize, sortField, sortDir);
    }

    @GetMapping("/list")
    public List<SubjectCategoryModel> getList() {
        return service.getList();
    }
}
