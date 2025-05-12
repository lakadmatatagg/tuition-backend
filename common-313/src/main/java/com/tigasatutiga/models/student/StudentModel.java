package com.tigasatutiga.models.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
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
    private ReferenceCodeModel grade;
    private List<ReferenceCodeModel> subjects;
    private ParentModel parent;
}
