package com.tigasatutiga.controller.documents;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.documents.RunningNoEntity;
import com.tigasatutiga.models.documents.RunningNoModel;
import com.tigasatutiga.service.documents.RunningNoSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/running-no")
public class RunningNoController extends BaseController<RunningNoEntity, RunningNoModel, Long> {

    @Autowired
    private RunningNoSO runningNoSO;

    public RunningNoController(RunningNoSO service) {
        super(service);
    }

    @GetMapping("/next")
    public RunningNoModel getNextRunningNo(
            @RequestParam String docCode,
            @RequestParam String prefix,
            @RequestParam(required = false) String suffix
    ) {
        return runningNoSO.getNextRunningNo(docCode);
    }
}
