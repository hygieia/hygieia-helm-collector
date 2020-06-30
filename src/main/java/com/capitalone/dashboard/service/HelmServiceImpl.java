package com.capitalone.dashboard.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class HelmServiceImpl implements HelmService {
	private static final Log LOG = LogFactory.getLog(HelmService.class);

	private boolean isConfigSet() {
		// return collectorItemRepository.findAll().iterator().hasNext();
		return false;
	}

	 
	 
}
