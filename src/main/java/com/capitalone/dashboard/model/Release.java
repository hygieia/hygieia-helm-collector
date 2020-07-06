package com.capitalone.dashboard.model;

import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.capitalone.dashboard.enums.HelmStatus;

/*Model to store the latest release Data*/

@Document(collection="helm_release")
public class Release extends BaseModel{
	@Indexed(unique = true)
	private String name;
	private String  version;
	private Long updated;
	private HelmStatus status;
	
	@Transient
	private String chart;
	
	public Release(String name, String version, Long updated, HelmStatus status, String chart) {
		//super.objectId = objectId;// private but not same package
		this.name = name;
		this.version = version;
		this.updated = updated;
		this.status = status;
		this.chart = chart;
	}
	
	public Release(String name, String version, Long updated, HelmStatus status) {
		//super.objectId = objectId;// private but not same package
		this.name = name;
		this.version = version;
		this.updated = updated;
		this.status = status;
	}

	
	
	public String getChart() {
		return chart;
	}

	public void setChart(String chart) {
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

}