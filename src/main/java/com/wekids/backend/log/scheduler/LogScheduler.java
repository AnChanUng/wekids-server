package com.wekids.backend.log.scheduler;

import com.wekids.backend.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class LogScheduler {
    private final LogService logService;

    @Scheduled(cron = "0 0 5 1 * ?", zone = "Asia/Seoul")
    @Transactional
    public void backup() {
        LocalDate start = LocalDate.now().minusDays(60);
        LocalDate end = LocalDate.now().minusDays(30);

        while (!start.isAfter(end)) {
            logService.backUp(start);
            start = start.plusDays(1);
        }
    }
}
