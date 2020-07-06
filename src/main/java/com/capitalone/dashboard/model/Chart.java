package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "helm_release_chart")
public class Chart extends BaseModel {
	private Long updated;
	private ObjectId releaseObjectId;
	private String status;
	private String chart;
	private String appVersion;
	private String description;

	public Chart(Long updated, String status, String chart,
			String appVersion, String description) {
		this.updated = updated;
		this.status = status;
		this.chart = chart;
		this.appVersion = appVersion;
		this.description = description;
	}
	
	

	public ObjectId getReleaseObjectId() {
		return releaseObjectId;
	}



	public void setReleaseObjectId(ObjectId releaseObjectId) {
		this.releaseObjectId = releaseObjectId;
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
