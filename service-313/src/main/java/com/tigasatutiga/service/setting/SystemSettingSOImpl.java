package com.tigasatutiga.service.setting;

import com.tigasatutiga.entities.setting.SystemSettingEntity;
import com.tigasatutiga.mapper.setting.SystemSettingMapper;
import com.tigasatutiga.models.setting.SystemSettingModel;
import com.tigasatutiga.repository.setting.SystemSettingRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.springframework.stereotype.Service;

@Service
public class SystemSettingSOImpl extends BaseSOImpl<SystemSettingEntity, SystemSettingModel, Long> implements SystemSettingSO {

    private final SystemSettingRepository repository;
    private final SystemSettingMapper mapper;

    public SystemSettingSOImpl(SystemSettingRepository repository, SystemSettingMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public SystemSettingModel getByKey(String key) {
        SystemSettingEntity entity = repository.findByKeyEquals(key);
        if (entity != null) {
            return mapper.toModel(entity);
        }
        return null;
    }
}
