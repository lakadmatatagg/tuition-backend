package com.tigasatutiga.mapper.tuitionez.student;

import com.tigasatutiga.entities.tuitionez.student.StudentEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.tuitionez.student.StudentModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper extends BaseMapper<StudentEntity, StudentModel> {
    StudentMapper STUDENT_MAPPER = Mappers.getMapper(StudentMapper.class);

    List<StudentEntity> toEntityList(List<StudentModel> modelList);
    List<StudentModel> toModelList(List<StudentEntity> entityList);
}
