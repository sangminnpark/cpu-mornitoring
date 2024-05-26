package com.terra.project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terra.project.entity.CpuUsageSummary;

public interface CpuUsageSummaryRepository extends JpaRepository<CpuUsageSummary, Long> {
    // Spring Data JPA가 기본 CRUD 메소드를 제공
	
	List<CpuUsageSummary> findAllByTypeAndCreatedAtBetweenOrderByCreatedAtAsc(
			String type,
			LocalDateTime startDateTime,
			LocalDateTime endDateTime);
	
}
