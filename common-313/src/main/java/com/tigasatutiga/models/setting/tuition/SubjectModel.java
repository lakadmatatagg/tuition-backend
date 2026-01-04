package com.tigasatutiga.models.setting.tuition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectModel {
    private Long id;
    private String subjectCode;
    private String subjectName;
    private SubjectCategoryModel category;
}
