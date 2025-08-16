package com.example.groupbooking.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BatchJobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processGroupBookingsJob;

    @Scheduled(fixedRate = 60000) // 每1分钟执行一次
    public void runProcessGroupBookingsJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("timestamp", LocalDateTime.now().toString())
                    .toJobParameters();
            
            jobLauncher.run(processGroupBookingsJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}