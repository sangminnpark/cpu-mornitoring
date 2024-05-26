package com.terra.project.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CpuUsageVO {
	private String time;
    private double cpuUsage; // for minutes
    
    private double minUsage;
    private double maxUsage;
    private double avgUsage;

    // for minutes
    public CpuUsageVO(LocalDateTime time, double cpuUsage) {
        this.time = formatDateTime(time);
        this.cpuUsage = cpuUsage;
    }
    
    public CpuUsageVO(LocalDateTime time, double minUsage, double maxUsage, double avgUsage) {
        this.time = formatDateTime(time);
        this.minUsage = minUsage;
        this.maxUsage = maxUsage;
        this.avgUsage = avgUsage;
    }
    
    public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public double getMinUsage() {
		return minUsage;
	}

	public void setMinUsage(double minUsage) {
		this.minUsage = minUsage;
	}

	public double getMaxUsage() {
		return maxUsage;
	}

	public void setMaxUsage(double maxUsage) {
		this.maxUsage = maxUsage;
	}

	public double getAvgUsage() {
		return avgUsage;
	}

	public void setAvgUsage(double avgUsage) {
		this.avgUsage = avgUsage;
	}

	public void setTime(LocalDateTime time) {
        this.time = formatDateTime(time);
    }
   
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}