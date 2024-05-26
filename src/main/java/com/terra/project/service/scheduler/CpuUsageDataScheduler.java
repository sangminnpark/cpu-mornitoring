package com.terra.project.service.scheduler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.terra.project.service.CpuUsageCollectSaveService;

@Component
public class CpuUsageDataScheduler {

    @Autowired
    private CpuUsageCollectSaveService cpuUsageCollectSaveService;

    @Scheduled(cron = "0 * * * * *")
    public void scheduleCpuDataMin() {
    	cpuUsageCollectSaveService.collectAndSaveCpuDataMin();
        System.out.println(new Date().toString());
    }
    
    @Scheduled(cron = "0 0 * * * *")
    public void scheduleCpuDataHour() {
    	cpuUsageCollectSaveService.collectAndSaveCpuDataHour();
    }
    
    @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    public void scheduleCpuDataDay() {
    	cpuUsageCollectSaveService.collectAndSaveCpuDataDay();
    }
}
