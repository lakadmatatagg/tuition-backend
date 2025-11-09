package com.tigasatutiga.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CronJobModel {
    private String jobMinute;
    private String jobHour;
    private String jobDayOfMonth;
}
