package com.tigasatutiga.controller.tuitionez.student;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.models.tuitionez.student.ParentModel;
import com.tigasatutiga.service.tuitionez.student.ParentSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/parent")
public class ParentController extends BaseController<ParentEntity, ParentModel, Long> {

    @Autowired
    private ParentSO parentSO;

    public ParentController(ParentSO service) { super(service); }
}
