package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.ComponentData;

public interface HelmService {
	
	
	  ComponentData getRelease();
	  
	  ComponentData getChart(String releaseId);
	  
	  ComponentData getRepo();
}
