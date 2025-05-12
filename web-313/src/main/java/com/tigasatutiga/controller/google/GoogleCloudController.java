package com.tigasatutiga.controller.google;

import com.google.cloud.scheduler.v1.CloudSchedulerClient;
import com.google.cloud.scheduler.v1.Job;
import com.google.cloud.scheduler.v1.JobName;
import com.google.protobuf.FieldMask;
import com.tigasatutiga.models.google.CronJobModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping("/cloud")
public class GoogleCloudController {
    @Value("${google.project-id}")
    private String projectId;

    @Value("${google.region}")
    private String region;

    @Value("${google.scheduler.id}")
    private String jobId;

    @PostMapping("/update-cron")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSchedulerJob(@RequestBody CronJobModel model) {
        try {
            int minute = Integer.parseInt(model.getJobMinute());
            int hour = Integer.parseInt(model.getJobHour());
            int day = Integer.parseInt(model.getJobDayOfMonth());

            if (day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid cron values.");
            }

            String cron = String.format("%d %d %d * *", minute, hour, day);

            try (CloudSchedulerClient client = CloudSchedulerClient.create()) {
                String jobName = JobName.of(projectId, region, jobId).toString();
                Job job = client.getJob(jobName);

                Job updatedJob = job.toBuilder()
                        .setSchedule(cron)
                        .build();

                FieldMask updateMask = FieldMask.newBuilder().addPaths("schedule").build();
                client.updateJob(updatedJob, updateMask);

                log.info("Cron updated to {}", cron);
            }

        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid number format.");
        } catch (Exception e) {
            log.error("Failed to update cron: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update cron.");
        }
    }

    @GetMapping("/current-cron")
    public CronJobModel getCurrentCron() {
        try (CloudSchedulerClient client = CloudSchedulerClient.create()) {
            String jobName = JobName.of(projectId, region, jobId).toString();
            Job job = client.getJob(jobName);
            String cron = job.getSchedule(); // e.g. "0 9 27 * *"

            String[] parts = cron.split(" ");
            if (parts.length < 3) {
                throw new IllegalArgumentException("Invalid cron expression: " + cron);
            }

            CronJobModel model = new CronJobModel();
            model.setJobMinute(parts[0]);
            model.setJobHour(parts[1]);
            model.setJobDayOfMonth(parts[2]);

            return model;
        } catch (Exception e) {
            log.error("Failed to fetch current cron: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get cron schedule");
        }
    }
}
