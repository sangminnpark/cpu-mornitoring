package com.terra.project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terra.project.entity.CpuUsage;

@Repository
public interface CpuUsageRepository extends JpaRepository<CpuUsage, Long> {
	List<CpuUsage> findAllByCreatedAtBetween(
			LocalDateTime hourStart,
			LocalDateTime hourEnd);
}
