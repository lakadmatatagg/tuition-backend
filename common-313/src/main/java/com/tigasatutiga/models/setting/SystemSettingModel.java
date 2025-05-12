package com.tigasatutiga.models.setting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemSettingModel {

    private Long id;

    private String key;

    private String value;

    private String description;
}
