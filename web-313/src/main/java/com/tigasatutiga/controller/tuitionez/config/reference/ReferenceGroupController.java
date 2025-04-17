package com.tigasatutiga.controller.tuitionez.config.reference;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceGroupEntity;
import com.tigasatutiga.models.tuitionez.config.reference.ReferenceGroupModel;
import com.tigasatutiga.service.tuitionez.config.reference.ReferenceGroupSO;
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
@RequestMapping("/config/reference-group")
public class ReferenceGroupController extends BaseController<ReferenceGroupEntity, ReferenceGroupModel, Long> {

    @Autowired
    private ReferenceGroupSO referenceGroupSO;

    public ReferenceGroupController(ReferenceGroupSO service) {super(service); }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<ReferenceGroupModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir) {
        return referenceGroupSO.getAll(pageNo, pageSize, sortField, sortDir);
    }

    @GetMapping("/list")
    public List<ReferenceGroupModel> getList() {
        return referenceGroupSO.getList();
    }

}
