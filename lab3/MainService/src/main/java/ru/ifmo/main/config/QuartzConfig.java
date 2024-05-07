package ru.ifmo.main.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.main.worker.ListingNotificationJob;

import static org.quartz.JobBuilder.newJob;

@Slf4j
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return newJob(ListingNotificationJob.class)
                .withIdentity("listingNotificationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("listingNotificationTrigger")
//                .withSchedule(dailyAtHourAndMinute(9, 0)) // Запуск каждый день в 9 утра
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)  // задаём интервал в одну минуту
                        .repeatForever())  // задача будет повторяться бесконечно
                .build();
    }
}
