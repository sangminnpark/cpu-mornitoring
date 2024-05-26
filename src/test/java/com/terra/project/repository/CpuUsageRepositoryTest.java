package com.terra.project.repository;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.terra.project.entity.CpuUsage;

@DataJpaTest
public class CpuUsageRepositoryTest {
	
	@Autowired
	private CpuUsageRepository cpuUsageRepository;
	
	@Test
    @DisplayName("분단위 수집이 잘 되는지 확인")
    void saveMinute() {
        // given
		CpuUsage cpuUsage = new CpuUsage(44, LocalDateTime.now().withSecond(0));
        // when
		CpuUsage savedCpuUsage = cpuUsageRepository.save(cpuUsage);
        // then
        Assertions.assertThat(cpuUsage).isSameAs(savedCpuUsage);
        Assertions.assertThat(cpuUsage.getCpuUsage()).isEqualTo(savedCpuUsage.getCpuUsage());
        Assertions.assertThat(cpuUsageRepository.count()).isEqualTo(1);
    }
}
