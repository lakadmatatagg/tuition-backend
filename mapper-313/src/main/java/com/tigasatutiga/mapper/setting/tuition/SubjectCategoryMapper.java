package com.tigasatutiga.mapper.setting.tuition;

import com.tigasatutiga.entities.setting.tuition.SubjectCategoryEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.setting.tuition.SubjectCategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectCategoryMapper
        extends BaseMapper<SubjectCategoryEntity, SubjectCategoryModel> {

    SubjectCategoryMapper SUBJECT_CATEGORY_MAPPER =
            Mappers.getMapper(SubjectCategoryMapper.class);

    List<SubjectCategoryModel> toModelList(List<SubjectCategoryEntity> entityList);
    List<SubjectCategoryEntity> toEntityList(List<SubjectCategoryModel> modelList);
}
