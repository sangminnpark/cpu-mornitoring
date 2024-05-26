package com.terra.project.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.terra.project.entity.CpuUsageSummary;

@DataJpaTest
public class CpuUsageSummaryRepositoryTest {

	@Autowired
	private CpuUsageSummaryRepository cpuUsageSummaryRepository;
	
	@Test
    @DisplayName("시단위 수집이 잘 되는지 확인")
    void check_save_by_hour() {
        // given
		CpuUsageSummary cpuUsageSummary = new CpuUsageSummary();
		cpuUsageSummary.setType("hour");
		cpuUsageSummary.setMin(0);
		cpuUsageSummary.setMax(40);
		cpuUsageSummary.setAvg(20);
		
        // when
		CpuUsageSummary savedCpuUsageSummary = cpuUsageSummaryRepository.save(cpuUsageSummary);
		
        // then
        Assertions.assertThat(cpuUsageSummary).isSameAs(savedCpuUsageSummary);
        Assertions.assertThat(cpuUsageSummary.getType()).isEqualTo(savedCpuUsageSummary.getType());
        Assertions.assertThat(cpuUsageSummaryRepository.count()).isEqualTo(1);
    }
	
	@Test
    @DisplayName("일단위 수집이 잘 되는지 확인")
    void check_save_by_day() {
        // given
		CpuUsageSummary cpuUsageSummary = new CpuUsageSummary();
		cpuUsageSummary.setType("day");
		cpuUsageSummary.setMin(0);
		cpuUsageSummary.setMax(40);
		cpuUsageSummary.setAvg(20);
		
        // when
		CpuUsageSummary savedCpuUsageSummary = cpuUsageSummaryRepository.save(cpuUsageSummary);
		
        // then
        Assertions.assertThat(cpuUsageSummary).isSameAs(savedCpuUsageSummary);
        Assertions.assertThat(cpuUsageSummary.getType()).isEqualTo(savedCpuUsageSummary.getType());
        Assertions.assertThat(cpuUsageSummaryRepository.count()).isEqualTo(1);
    }
}
