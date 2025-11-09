package com.tigasatutiga.controller.reference;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceCodeEntity;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.service.reference.ReferenceCodeSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/config/reference-code")
public class ReferenceCodeController extends BaseController<ReferenceCodeEntity, ReferenceCodeModel, Long> {

    @Autowired
    private ReferenceCodeSO referenceCodeSO;

    public ReferenceCodeController(ReferenceCodeSO service) { super(service); }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<ReferenceCodeModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir) {
        return referenceCodeSO.getAll(pageNo, pageSize, sortField, sortDir);
    }

    @GetMapping("/list/{groupCode}")
    public List<ReferenceCodeModel> getList(@PathVariable String groupCode) {
        return referenceCodeSO.getListByGroup(groupCode);
    }
}
