package com.tigasatutiga.mapper.tuitionez.config.reference;

import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceCodeEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.tuitionez.config.reference.ReferenceCodeModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReferenceCodeMapper extends BaseMapper<ReferenceCodeEntity, ReferenceCodeModel> {
    ReferenceCodeMapper REFERENCE_CODE_MAPPER = Mappers.getMapper(ReferenceCodeMapper.class);

    List<ReferenceCodeModel> toModelList(List<ReferenceCodeEntity> entityList);

    List<ReferenceCodeEntity> toEntityList(List<ReferenceCodeModel> modelList);
}
