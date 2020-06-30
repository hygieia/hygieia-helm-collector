package com.capitalone.dashboard.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.capitalone.dashboard.enums.HelmStatus;

/*Model to store the latest release Data*/

@Document(collection="helm_release")
public class Release extends BaseModel{
	private String name;
	private String  version;
	private Long updated;
	private HelmStatus status;
	private Chart currentChart;
	private List<Chart> chart;
	
	public Release(String name, String version, Long updated, HelmStatus status, Chart currentChart) {
		super();
		this.name = name;
		this.version = version;
		this.updated = updated;
		this.status = status;
		this.currentChart = currentChart;
	}
	
	public Release(String name, String version, Long updated, HelmStatus status, Chart currentChart,
			List<Chart> chart) {
		super();
		this.name = name;
		this.version = version;
		this.updated = updated;
		this.status = status;
		this.currentChart = currentChart;
		this.chart = chart;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Long getUpdated() {
		return updated;
	}
	public void setUpdated(Long updated) {
		this.updated = updated;
	}
	public HelmStatus getStatus() {
		return status;
	}
	public void setStatus(HelmStatus status) {
		this.status = status;
	}
	public Chart getCurrentChart() {
		return currentChart;
	}
	public void setCurrentChart(Chart currentChart) {
		this.currentChart = currentChart;
	}
	public List<Chart> getChart() {
		return chart;
	}
	public void setChart(List<Chart> chart) {
		this.chart = chart;
	}

	
}