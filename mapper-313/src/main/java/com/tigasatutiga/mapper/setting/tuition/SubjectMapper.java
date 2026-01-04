package com.tigasatutiga.mapper.setting.tuition;

import com.tigasatutiga.entities.setting.tuition.SubjectEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.setting.tuition.SubjectModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper
        extends BaseMapper<SubjectEntity, SubjectModel> {

    SubjectMapper SUBJECT_MAPPER =
            Mappers.getMapper(SubjectMapper.class);

    List<SubjectModel> toModelList(List<SubjectEntity> entityList);
    List<SubjectEntity> toEntityList(List<SubjectModel> modelList);
}
