package com.tigasatutiga.models.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentModel {
    private Long id;
    private String name;
    private String phoneNo;
    private String telegramChatId;
}
