package com.tigasatutiga.models.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RunningNoModel {
    private Long id;
    private String docCode;
    private String prefix;
    private String runningNo;
    private String suffix;
}