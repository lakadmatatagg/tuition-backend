package com.tigasatutiga.service.setting;

import com.tigasatutiga.entities.setting.SystemSettingEntity;
import com.tigasatutiga.models.setting.SystemSettingModel;
import com.tigasatutiga.service.BaseSO;

public interface SystemSettingSO extends BaseSO<SystemSettingEntity, SystemSettingModel, Long> {
    SystemSettingModel getByKey(String key);
}
