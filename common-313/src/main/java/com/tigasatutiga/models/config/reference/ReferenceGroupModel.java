package com.tigasatutiga.models.config.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferenceGroupModel {
    private Long id;
    private String code;
    private String name;
    private String description;
    private boolean active;
}
