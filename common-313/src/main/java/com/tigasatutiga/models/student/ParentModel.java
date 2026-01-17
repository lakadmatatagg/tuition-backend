package com.tigasatutiga.models.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentModel {
    private Long id;
    private String name;
    private String phoneNo;
    private LocalDate registrationDate;
    private String telegramChatId;
}
