package com.capitalone.dashboard.model;

import com.capitalone.dashboard.enums.HelmStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/*Model to store the latest release Data*/

@Document(collection="helm_release")
@CompoundIndexes({
		@CompoundIndex(name = "release_namespace", def = "{'name' : 1, 'namespace': 1}")
})
public class Release extends BaseModel{
	private String name;
	private String  version;
	private Long updated;
	private HelmStatus status;
	private String namespace;
	@Transient
	private String chart;

	public Release() {
		super();
	}

	public Release(String name, String version, Long updated, HelmStatus status, String chart, String namespace) {
		this.name = name;
		this.version = version;
		this.updated = updated;
		this.status = status;
		this.chart = chart;
		this.namespace = namespace;
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

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(final String namespace) {
		this.namespace = namespace;
	}
}