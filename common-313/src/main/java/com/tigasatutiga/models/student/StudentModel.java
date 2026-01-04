package com.tigasatutiga.models.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.models.setting.tuition.SubjectCategoryModel;
import com.tigasatutiga.models.setting.tuition.SubjectModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentModel {
    private Long id;
    private String mykid;
    private String name;
    private List<SubjectCategoryModel> grades;
    private List<SubjectModel> subjects;
    private ParentModel parent;
}
