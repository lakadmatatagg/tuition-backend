package com.tigasatutiga.models.tuitionez.config.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferenceCodeModel {
    private Long id;
    private ReferenceGroupModel group;
    private String code;
    private String name;
    private int order;
    private boolean active;
}
