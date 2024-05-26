package com.terra.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terra.project.entity.CpuUsage;
import com.terra.project.entity.CpuUsageSummary;
import com.terra.project.repository.CpuUsageRepository;
import com.terra.project.repository.CpuUsageSummaryRepository;
import com.terra.project.vo.CpuUsageVO;

@Service
public class CpuUsageService {

	@Autowired
	private CpuUsageRepository cpuDataRepository;

	@Autowired
	private CpuUsageSummaryRepository cpuDataSummaryRepository;

	// 분단위조회
	public List<CpuUsageVO> getCpuUsageByMinutes(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		List<CpuUsageVO> res = new ArrayList<>();

		// 시간 조정
		// 1. 최근 1주일데이터만 제공
		LocalDateTime oneWeekAgo = LocalDateTime.now().minus(1, ChronoUnit.WEEKS);
		if (startDateTime.isBefore(oneWeekAgo)) {
			startDateTime = oneWeekAgo;
		}

		// 2. 현재 시간까지만 제공
		if (endDateTime.isAfter(LocalDateTime.now())) {
			endDateTime = LocalDateTime.now();
		}

		List<CpuUsage> ori = cpuDataRepository.findAllByCreatedAtBetween(startDateTime.withMinute(0),
				endDateTime.withMinute(59));
		for (CpuUsage cpuData : ori) {
			CpuUsageVO cpuUsage = new CpuUsageVO(cpuData.getCreatedAt(), cpuData.getCpuUsage());
			res.add(cpuUsage);
		}
		return res;
	}

	// 시단위 조회
	public List<CpuUsageVO> getCpuUsageByHour(LocalDate targetDate) {
		List<CpuUsageVO> res = new ArrayList<>();
		// 시작일을 자정으로 설정
		LocalDateTime startDateTime = targetDate.atTime(LocalTime.MIN);
		// 종료일을 그 날의 23시 59분 59초로 설정
		LocalDateTime endDateTime = targetDate.atTime(LocalTime.MAX);

		// 시간 조정
		// 1. 최근 3달 데이터만 제공
		LocalDateTime threeMonthAgo = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);
		if (startDateTime.isBefore(threeMonthAgo)) {
			startDateTime = threeMonthAgo;
		}

		// 2. 현재 시간까지만 제공
		if (endDateTime.isAfter(LocalDateTime.now())) {
			endDateTime = LocalDateTime.now();
		}

		List<CpuUsageSummary> ori = cpuDataSummaryRepository.findAllByTypeAndCreatedAtBetweenOrderByCreatedAtAsc("hour",
				startDateTime, endDateTime);
		for (CpuUsageSummary cpuDataSummary : ori) {
			CpuUsageVO cpuUsageVO = new CpuUsageVO(cpuDataSummary.getCreatedAt().withMinute(0), cpuDataSummary.getMin(),
					cpuDataSummary.getMax(), cpuDataSummary.getAvg());
			res.add(cpuUsageVO);
		}
		return res;
	}

	// 일단위 조회
	public List<CpuUsageVO> getCpuUsageByDaily(LocalDate startDate, LocalDate endDate) {
		List<CpuUsageVO> res = new ArrayList<>();
		// 시작일을 자정으로 설정
		LocalDateTime startDateTime = startDate.atTime(LocalTime.MIN);
		// 종료일을 그 날의 23시 59분 59초로 설정
		LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

		// 시간 조정
		// 1. 최근 1년 데이터만 제공
		LocalDateTime oneYearsAgo = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
		if (startDateTime.isBefore(oneYearsAgo)) {
			startDateTime = oneYearsAgo;
		}

		// 2. 현재 시간까지만 제공
		if (endDateTime.isAfter(LocalDateTime.now())) {
			endDateTime = LocalDateTime.now();
		}

		List<CpuUsageSummary> ori = cpuDataSummaryRepository.findAllByTypeAndCreatedAtBetweenOrderByCreatedAtAsc("day",
				startDateTime, endDateTime);

		for (CpuUsageSummary cpuDataSummary : ori) {
			CpuUsageVO cpuUsageVO = new CpuUsageVO(cpuDataSummary.getCreatedAt().withMinute(0), cpuDataSummary.getMin(),
					cpuDataSummary.getMax(), cpuDataSummary.getAvg());
			res.add(cpuUsageVO);
		}
		return res;
	}

	
}