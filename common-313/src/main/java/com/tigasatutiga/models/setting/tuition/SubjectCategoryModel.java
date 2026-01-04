package com.tigasatutiga.models.setting.tuition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectCategoryModel {
    private Long id;
    private String categoryCode;
    private String categoryName;
    private BigDecimal price;
}
