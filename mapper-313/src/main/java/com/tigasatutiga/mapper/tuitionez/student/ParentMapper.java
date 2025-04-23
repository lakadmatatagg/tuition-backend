package com.tigasatutiga.mapper.tuitionez.student;

import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.tuitionez.student.ParentModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParentMapper extends BaseMapper<ParentEntity, ParentModel> {
    ParentMapper PARENT_MAPPER = Mappers.getMapper(ParentMapper.class);

    List<ParentModel> toModelList(List<ParentEntity> entityList);
    List<ParentEntity> toEntityList(List<ParentModel> modelList);
}
