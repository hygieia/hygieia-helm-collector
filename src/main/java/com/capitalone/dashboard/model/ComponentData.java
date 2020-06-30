package com.capitalone.dashboard.model;

import com.capitalone.dashboard.enums.HelmStatus;

/*The Source of data to specifc to each chart component rendered on the screen*/
public class ComponentData {
	
	private HelmStatus status;

	public HelmStatus getStatus() {
		return status;
	}

	public void setStatus(HelmStatus status) {
		this.status = status;
	}
	
	;
	
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object object) {
		this.data = object;
	}
	
	

}
