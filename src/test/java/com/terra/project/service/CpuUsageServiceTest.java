package com.terra.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.terra.project.vo.CpuUsageVO;

@ExtendWith(MockitoExtension.class)
public class CpuUsageServiceTest {

	@Mock
    private CpuUsageRepository cpuUsageRepository;
	
	@Mock
	private CpuUsageSummaryRepository cpuDataSummaryRepository;

    @InjectMocks
    private CpuUsageService cpuUsageService;

    @Test
    @DisplayName("분단위 cpu 사용률을 제대로 가져오는지 확인")
    public void when_Get_CpuUsage_For_Minutes() {
    	// given
    	CpuUsage cpuUsageMin = new CpuUsage(44, LocalDateTime.now().minus(5, ChronoUnit.DAYS).withSecond(0));
    	List<CpuUsage> mockList = new ArrayList<>();
    	mockList.add(cpuUsageMin);
    	
    	when(cpuUsageRepository.findAllByCreatedAtBetween(any(), any())).thenReturn(mockList);
    	
        // when
        List<CpuUsageVO> result = cpuUsageService.getCpuUsageByMinutes(LocalDateTime.now().minus(1, ChronoUnit.WEEKS), LocalDateTime.now());

        // then
        assertEquals(44, result.get(0).getCpuUsage());
    }
    
    @Test
    @DisplayName("시단위 cpu 사용률을 제대로 가져오는지 확인")
    public void when_Get_CpuUsage_For_Hour() {
    	// given
    	CpuUsageSummary cpuUsageHour = new CpuUsageSummary("hour", 0, 40, 20, LocalDateTime.now());
    	List<CpuUsageSummary> mockList = new ArrayList<>();
    	mockList.add(cpuUsageHour);
    	
    	when(cpuDataSummaryRepository.findAllByTypeAndCreatedAtBetweenOrderByCreatedAtAsc(any(), any(), any())).thenReturn(mockList);
    	
        // when
        List<CpuUsageVO> result = cpuUsageService.getCpuUsageByHour(LocalDate.now().minusDays(5));

        // then
        assertEquals(0, result.get(0).getMinUsage());
        assertEquals(40, result.get(0).getMaxUsage());
        assertEquals(20, result.get(0).getAvgUsage());
    }
    
    @Test
    @DisplayName("일단위 cpu 사용률을 제대로 가져오는지 확인")
    public void when_Get_CpuUsage_For_Day() {
    	// given
    	CpuUsageSummary cpuUsageHour = new CpuUsageSummary("day", 0, 40, 20, LocalDateTime.now());
    	List<CpuUsageSummary> mockList = new ArrayList<>();
    	mockList.add(cpuUsageHour);
    	
    	when(cpuDataSummaryRepository.findAllByTypeAndCreatedAtBetweenOrderByCreatedAtAsc(any(), any(), any())).thenReturn(mockList);
    	
        // when
        List<CpuUsageVO> result = cpuUsageService.getCpuUsageByDaily(LocalDate.now().minusDays(5), LocalDate.now());

        // then
        assertEquals(0, result.get(0).getMinUsage());
        assertEquals(40, result.get(0).getMaxUsage());
        assertEquals(20, result.get(0).getAvgUsage());
    }
}
