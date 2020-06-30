package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "helm_release_chart")
public class Chart  extends BaseModel{
	private Long updated;
	private String status;
	private String chart;
	private String appVersion;
	private String description;

	// flash the high cohesive in classes here using telescopic contructor
	public Chart(Long updated, String status, String chart, String appVersion, String description) {
		super();
		this.updated = updated;
		this.status = status;
		this.chart = chart;
		this.appVersion = appVersion;
		this.description = description;
	}
	
	public String getChart() {
		return chart;
	}
	public void setChart(String chart) {
		this.chart = chart;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public Long getUpdated() {
		return updated;
	}
	public void setUpdated(Long updated) {
		this.updated = updated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
