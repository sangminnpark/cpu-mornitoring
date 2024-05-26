package com.terra.project.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terra.project.service.CpuUsageService;
import com.terra.project.vo.CpuUsageVO;

@RestController
@RequestMapping("monitoring/cpu-usage") // 기본 URL 경로
public class CpuUsageController {
    @Autowired // 의존성 주입
    private CpuUsageService cpuUsageService;

    @GetMapping("/minute")
    public ResponseEntity<List<CpuUsageVO>> getCpuUsageMinute(
    		@RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
    	List<CpuUsageVO> res = cpuUsageService.getCpuUsageByMinutes(startDateTime, endDateTime);
    		
    	return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    
    @GetMapping("/hours")
    public ResponseEntity<List<CpuUsageVO>> getCpuUsageHour(
    		@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
    	List<CpuUsageVO> res = cpuUsageService.getCpuUsageByHour(targetDate);
    	return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    
    @GetMapping("/daily")
    public ResponseEntity<List<CpuUsageVO>> getCpuUsageDaily(
    		@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    	List<CpuUsageVO> res = cpuUsageService.getCpuUsageByDaily(startDate, endDate);
    		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    		
    	return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    
}
