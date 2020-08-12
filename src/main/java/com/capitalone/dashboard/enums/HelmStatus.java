package com.capitalone.dashboard.enums;

/*The Status specific to the Hygieia Docker Collector*/
public enum HelmStatus {

	UNKNOWN, 
	DEPLOYED, UNINSTALLED, SUPERSEDED, FAILED, UNINSTALLING, 
	PENDING_INSTALL, 
	PENDING_UPGRADE, 
	PENDING_ROLLBACK;
	
}
