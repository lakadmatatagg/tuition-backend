package com.tigasatutiga.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.scheduler.v1.CloudSchedulerClient;
import com.google.cloud.scheduler.v1.CloudSchedulerSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class GoogleCloudConfig {

    @Value("${google.key}")
    private String credentialsPath;  // Path to the service account JSON file

    @Bean
    public CloudSchedulerClient cloudSchedulerClient() throws IOException {
        InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream(credentialsPath.replace("classpath:", ""));

        // Log the status of the input stream
        if (credentialsStream == null) {
            log.error("Google Cloud credentials file not found at {}", credentialsPath);
            throw new IOException("Google Cloud credentials file not found at " + credentialsPath);
        }

        try {
            // Log the stream size for diagnostic purposes
            int availableBytes = credentialsStream.available();
            log.info("Google Cloud credentials file loaded successfully. Size: {} bytes", availableBytes);

            // Load credentials from the service account JSON file
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

            // Configure the Cloud Scheduler client with the credentials
            CloudSchedulerSettings schedulerSettings = CloudSchedulerSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials)
                    .build();

            log.info("Google Cloud credential authentication successful");

            // Return the CloudSchedulerClient configured with the credentials
            return CloudSchedulerClient.create(schedulerSettings);
        } catch (IOException e) {
            log.error("Error loading Google Cloud credentials from stream", e);
            throw e;
        } finally {
            // Close the input stream to prevent memory leaks
            credentialsStream.close();
        }
    }
}
