package com.tigasatutiga.mapper.documents;

import com.tigasatutiga.entities.documents.RunningNoEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.documents.RunningNoModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RunningNoMapper extends BaseMapper<RunningNoEntity, RunningNoModel> {
    RunningNoMapper RUNNING_NO_MAPPER = Mappers.getMapper(RunningNoMapper.class);

    List<RunningNoModel> toModelList(List<RunningNoEntity> entities);
    List<RunningNoEntity> toEntityList(List<RunningNoModel> models);
}