package com.capitalone.dashboard.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capitalone.dashboard.model.ComponentData;
import com.capitalone.dashboard.service.HelmServiceImpl;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HelmController {
	private static final Log LOG = LogFactory.getLog(HelmController.class);

	@Autowired
	private HelmServiceImpl helmService;

	@RequestMapping(value = "/helm/release/", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<ComponentData> getRelease() {
		return ResponseEntity.ok(helmService.getRelease());
	}

	@RequestMapping(value = "/helm/chart/{releaseId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<ComponentData> getChart(String releaseId) {
		return ResponseEntity.ok(helmService.getChart(releaseId));
	}

	@RequestMapping(value = "/helm/repo/", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<ComponentData> getRepo() {
		return ResponseEntity.ok(helmService.getRepo());
	}

}