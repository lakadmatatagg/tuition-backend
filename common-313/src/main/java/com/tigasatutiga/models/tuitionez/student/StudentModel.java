package com.tigasatutiga.models.tuitionez.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigasatutiga.entities.tuitionez.student.StudentEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentModel {
    private Long id;
    private String name;
    private String phoneNo;
    private List<StudentEntity> students;
}
