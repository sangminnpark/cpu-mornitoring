package com.terra.project.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.terra.project.service.CpuUsageService;
import com.terra.project.vo.CpuUsageVO;

@WebMvcTest(CpuUsageController.class)
@AutoConfigureMockMvc
public class CpuUsageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CpuUsageService cpuUsageService;

    private List<CpuUsageVO> mockCpuUsageList;

    @BeforeEach
    public void setup() {
        CpuUsageVO cpuUsage1 = new CpuUsageVO(LocalDateTime.of(2023, 5, 1, 0, 0), 20.5);
        CpuUsageVO cpuUsage2 = new CpuUsageVO(LocalDateTime.of(2023, 5, 1, 1, 0), 25.3);
        mockCpuUsageList = Arrays.asList(cpuUsage1, cpuUsage2);
    }

    @Test
    public void testGetCpuUsageMinute() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 5, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 5, 1, 1, 0);

        when(cpuUsageService.getCpuUsageByMinutes(startDateTime, endDateTime)).thenReturn(mockCpuUsageList);

        mockMvc.perform(get("/monitoring/cpu-usage/minute")
                .param("startDateTime", startDateTime.toString())
                .param("endDateTime", endDateTime.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    public void testGetCpuUsageHour() throws Exception {
        LocalDate targetDate = LocalDate.of(2023, 5, 1);

        when(cpuUsageService.getCpuUsageByHour(targetDate)).thenReturn(mockCpuUsageList);

        mockMvc.perform(get("/monitoring/cpu-usage/hours")
                .param("startDate", targetDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    public void testGetCpuUsageDaily() throws Exception {
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 5, 2);

        when(cpuUsageService.getCpuUsageByDaily(startDate, endDate)).thenReturn(mockCpuUsageList);

        mockMvc.perform(get("/monitoring/cpu-usage/daily")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }
}
