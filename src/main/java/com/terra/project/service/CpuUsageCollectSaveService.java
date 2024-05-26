package com.terra.project.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terra.project.entity.CpuUsage;
import com.terra.project.entity.CpuUsageSummary;
import com.terra.project.repository.CpuUsageRepository;
import com.terra.project.repository.CpuUsageSummaryRepository;

@Service
public class CpuUsageCollectSaveService {

	@Autowired
	private CpuUsageRepository cpuUsageRepository;

	@Autowired
	private CpuUsageSummaryRepository cpuDataSummaryRepository;

	private static final Logger logger = LoggerFactory.getLogger(CpuUsageService.class);

	public void collectAndSaveCpuDataMin() {

		double usage = 0;
		try {
			Process process = Runtime.getRuntime()
					.exec(new String[] { "sh", "-c", "ps -A -o %cpu | awk '{s+=$1} END {print s}'" });
			process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;

			if ((line = reader.readLine()) != null) {

				usage = Double.parseDouble(line.trim());
				System.out.println("CPU 사용률: " + usage + "%");
			}
			
			reader.close();
			CpuUsage cpuDataMin = new CpuUsage();
			cpuDataMin.setCpuUsage(usage);
			cpuDataMin.setCreatedAt(LocalDateTime.now().withSecond(0));

			cpuUsageRepository.save(cpuDataMin);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("collect error in collectAndSaveCpuDataMin... : " + e.getMessage());
			
			CpuUsage defaultUsage = new CpuUsage();
			defaultUsage.setCpuUsage(0);
			defaultUsage.setCreatedAt(LocalDateTime.now().withSecond(0));
			cpuUsageRepository.save(defaultUsage);
			logger.info("save default usage data at : {}", LocalDateTime.now().withSecond(0));
		}
	}

	public void collectAndSaveCpuDataHour() {
		LocalDateTime now = LocalDateTime.now();

		try {
			List<CpuUsage> usageList = cpuUsageRepository.findAllByCreatedAtBetween(now.withMinute(0),
					now.withMinute(59));

			List<Double> usages = usageList.stream().map(CpuUsage::getCpuUsage).collect(Collectors.toList());

			// 최소값 구하기
			double min = usages.stream().mapToDouble(Double::doubleValue).min().orElseThrow();

			// 최대값 구하기
			double max = usages.stream().mapToDouble(Double::doubleValue).max().orElseThrow();

			// 평균값 구하기
			double sum = usages.stream().mapToDouble(Double::doubleValue).sum();
			double avg = sum / usages.size();

			CpuUsageSummary cpu = new CpuUsageSummary();
			cpu.setType("hour");
			cpu.setMin(min);
			cpu.setMax(max);
			cpu.setAvg(avg);
			cpu.setCreatedAt(now.withMinute(0).withSecond(0));
			cpuDataSummaryRepository.save(cpu);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("collect error in collectAndSaveCpuDataHour... : " + e.getMessage());
		}
	}

	public void collectAndSaveCpuDataDay() {

		try {
			LocalDateTime now = LocalDateTime.now();

			List<CpuUsage> usageList = cpuUsageRepository.findAllByCreatedAtBetween(now.withHour(0), now.withHour(23));

			List<Double> usages = usageList.stream().map(CpuUsage::getCpuUsage).collect(Collectors.toList());

			// min
			double min = usages.stream().mapToDouble(Double::doubleValue).min().orElseThrow();

			// max
			double max = usages.stream().mapToDouble(Double::doubleValue).max().orElseThrow();

			// avg
			double sum = usages.stream().mapToDouble(Double::doubleValue).sum();
			double avg = sum / usages.size();

			CpuUsageSummary cpu = new CpuUsageSummary();
			cpu.setType("day");
			cpu.setMin(min);
			cpu.setMax(max);
			cpu.setAvg(avg);
			cpu.setCreatedAt(now.withHour(0));

			cpuDataSummaryRepository.save(cpu);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("collect error in collectAndSaveCpuDataDay... : " + e.getMessage());
		}
	}
}
