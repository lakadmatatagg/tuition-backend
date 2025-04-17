package com.tigasatutiga.mapper.tuitionez.config.reference;

import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceGroupEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.tuitionez.config.reference.ReferenceGroupModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReferenceGroupMapper extends BaseMapper<ReferenceGroupEntity, ReferenceGroupModel> {
    ReferenceGroupMapper REFERENCE_GROUP_MAPPER = Mappers.getMapper(ReferenceGroupMapper.class);

    List<ReferenceGroupModel> toModelList(List<ReferenceGroupEntity> entityList);

    List<ReferenceGroupEntity> toEntityList(List<ReferenceGroupModel> modelList);
}
