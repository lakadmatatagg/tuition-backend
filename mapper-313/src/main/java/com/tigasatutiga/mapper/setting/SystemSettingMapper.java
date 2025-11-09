package com.tigasatutiga.mapper.setting;

import com.tigasatutiga.entities.setting.SystemSettingEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.setting.SystemSettingModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SystemSettingMapper extends BaseMapper<SystemSettingEntity, SystemSettingModel> {
    SystemSettingMapper SYSTEM_SETTING_MAPPER = Mappers.getMapper(SystemSettingMapper.class);
}
