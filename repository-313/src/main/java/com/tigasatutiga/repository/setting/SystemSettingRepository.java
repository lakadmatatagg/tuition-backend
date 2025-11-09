package com.tigasatutiga.repository.setting;

import com.tigasatutiga.entities.setting.SystemSettingEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingRepository extends BaseRepository<SystemSettingEntity, Long> {

    SystemSettingEntity findByKeyEquals(String key);
}
