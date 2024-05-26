package com.terra.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.terra.project.entity.CpuUsage;
import com.terra.project.entity.CpuUsageSummary;
import com.terra.project.repository.CpuUsageRepository;
import com.terra.project.repository.CpuUsageSummaryRepository;

@ExtendWith(MockitoExtension.class)
public class CpuUsageCollectSaveServiceTest {

	@Mock
    private CpuUsageRepository cpuUsageRepository;
	
	@Mock
	private CpuUsageSummaryRepository cpuUsageSummaryRepository;

    @InjectMocks
    private CpuUsageCollectSaveService cpuUsageCollectSaveService;

    @Test
    @DisplayName("cpu 사용률을 시단위로 제대로 수집 확인")
    public void when_Collect_CpuUsage_For_Hours() {
    	// given
    	List<CpuUsage> mockList = new ArrayList<>();
    	mockList.add(new CpuUsage(44, LocalDateTime.now().minus(30, ChronoUnit.MINUTES).withSecond(0)));
    	mockList.add(new CpuUsage(22, LocalDateTime.now().minus(10, ChronoUnit.MINUTES).withSecond(0)));
    	
    	when(cpuUsageRepository.findAllByCreatedAtBetween(any(), any())).thenReturn(mockList);
    	
    	CpuUsageSummary cpuUsageSummary = new CpuUsageSummary("hour", 22, 44, 33, LocalDateTime.now());
    	when(cpuUsageSummaryRepository.save(any())).thenReturn(cpuUsageSummary);
    	
        // when
    	cpuUsageCollectSaveService.collectAndSaveCpuDataHour();

        // then
        assertEquals("hour", cpuUsageSummary.getType());
        assertEquals(44, cpuUsageSummary.getMax());
        assertEquals(22, cpuUsageSummary.getMin());
        assertEquals(33, cpuUsageSummary.getAvg());
    }
    
    @Test
    @DisplayName("cpu 사용률을 일단위로 제대로 수집 확인")
    public void when_Collect_CpuUsage_For_Days() {
    	// given
    	List<CpuUsage> mockList = new ArrayList<>();
    	mockList.add(new CpuUsage(44, LocalDateTime.now().minus(1, ChronoUnit.HOURS).withSecond(0)));
    	mockList.add(new CpuUsage(22, LocalDateTime.now().minus(4, ChronoUnit.HOURS).withSecond(0)));
    	
    	when(cpuUsageRepository.findAllByCreatedAtBetween(any(), any())).thenReturn(mockList);
    	
    	CpuUsageSummary cpuUsageSummary = new CpuUsageSummary("day", 22, 44, 33, LocalDateTime.now());
    	when(cpuUsageSummaryRepository.save(any())).thenReturn(cpuUsageSummary);
    	
        // when
    	cpuUsageCollectSaveService.collectAndSaveCpuDataDay();

        // then
        assertEquals("day", cpuUsageSummary.getType());
        assertEquals(44, cpuUsageSummary.getMax());
        assertEquals(22, cpuUsageSummary.getMin());
        assertEquals(33, cpuUsageSummary.getAvg());
    }
}
