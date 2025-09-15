package com.tigasatutiga.controller.student;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.models.student.ParentModel;
import com.tigasatutiga.service.student.ParentSO;
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
@RequestMapping("/parent")
public class ParentController extends BaseController<ParentEntity, ParentModel, Long> {

    @Autowired
    private ParentSO parentSO;

    public ParentController(ParentSO service) { super(service); }

    @GetMapping("/page/{pageNo}/{pageSize}/{sortField}/{sortDir}")
    public Page<ParentModel> getPage(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortField, @PathVariable String sortDir) {
        return parentSO.getAll(pageNo, pageSize, sortField, sortDir);
    }

    @GetMapping("/list")
    public List<ParentModel> getList() {
        return parentSO.getAll();
    }

    @GetMapping("/get-by-phone/{phone}")
    public ParentModel getByPhone(@PathVariable String phone) {
        return parentSO.getByPhone(phone);
    }

    @GetMapping("/get-by-telegram/{telegramId}")
    public ParentModel getByTelegram(@PathVariable String telegramId) {
        return parentSO.getByTelegram(telegramId);
    }
}
