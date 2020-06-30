package com.capitalone.dashboard.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.capitalone.dashboard.enums.HelmStatus;

/*Model to store the latest release Data*/

@Document(collection="helm_release")
public class Release extends BaseModel{
	private String releaseName;
	private String  revision;
	private String updated;
	private HelmStatus curentStatus;
	private Chart chart;
	private History history;
	private String appVersion;
	private String namespace;
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public HelmStatus getCurentStatus() {
		return curentStatus;
	}
	public void setCurentStatus(HelmStatus curentStatus) {
		this.curentStatus = curentStatus;
	}
	public Chart getChart() {
		return chart;
	}
	public void setChart(Chart chart) {
		this.chart = chart;
	}
	public History getHistory() {
		return history;
	}
	public void setHistory(History history) {
		this.history = history;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
}