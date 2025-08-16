package com.example.groupbooking.config;

import com.example.groupbooking.batch.BookingTransItemProcessor;
import com.example.groupbooking.batch.BookingTransItemReader;
import com.example.groupbooking.batch.BookingTransItemWriter;
import com.example.groupbooking.entity.BookingTrans;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableScheduling
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private BookingTransItemReader bookingTransItemReader;

    @Autowired
    private BookingTransItemProcessor bookingTransItemProcessor;

    @Autowired
    private BookingTransItemWriter bookingTransItemWriter;

    @Bean
    public Job processGroupBookingsJob() {
        return new JobBuilder("processGroupBookingsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(processBookingTransStep())
                .build();
    }

    @Bean
    public Step processBookingTransStep() {
        return new StepBuilder("processBookingTransStep", jobRepository)
                .<BookingTrans, BookingTrans>chunk(10, transactionManager)
                .reader(bookingTransItemReader)
                .processor(bookingTransItemProcessor)
                .writer(bookingTransItemWriter)
                .build();
    }
}