package com.terra.project.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;

@Entity
//@Data
public class CpuUsageSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // hour, day
    private double min;
    private double max;
    private double avg;
    private LocalDateTime createdAt;
    
    public CpuUsageSummary() {}

    public CpuUsageSummary(String type, double min, double max, double avg, LocalDateTime createdAt) {
		this.type = type;
		this.min = min;
		this.max = max;
		this.avg = avg;
		this.createdAt = createdAt;
	}

	// Getters and Setters
    public Long getId() {
        return id;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public void setId(Long id) {
        this.id = id;
    }


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}