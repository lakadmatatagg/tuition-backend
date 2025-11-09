package com.tigasatutiga.controller.setting;

import com.tigasatutiga.controller.BaseController;
import com.tigasatutiga.entities.setting.SystemSettingEntity;
import com.tigasatutiga.models.setting.SystemSettingModel;
import com.tigasatutiga.service.setting.SystemSettingSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/system-setting")
public class SystemSettingController extends BaseController<SystemSettingEntity, SystemSettingModel, Long> {

    @Autowired
    private SystemSettingSO systemSettingSO;

    public SystemSettingController(SystemSettingSO systemSettingSO) {
        super(systemSettingSO);
    }

    @GetMapping("/by-key/{key}")
    public SystemSettingModel findByKey(@PathVariable String key) {
        SystemSettingModel returnModel = null;
        try {
            returnModel = systemSettingSO.getByKey(key);
        } catch (Exception e) {
            log.error("ERROR SystemSettingController.findByKey, {}", e.getMessage());
        }
        return returnModel;
    }
}
