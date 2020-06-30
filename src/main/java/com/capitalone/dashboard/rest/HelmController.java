package com.capitalone.dashboard.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.capitalone.dashboard.service.HelmServiceImpl;

@RestController
public class HelmController {
	private static final Log LOG = LogFactory.getLog(HelmController.class);

	private final HelmServiceImpl helmService;

	@Autowired
	public HelmController(HelmServiceImpl helmService) {
		this.helmService = helmService;
	}

	/*
	 * @RequestMapping(value = "/collector/helm/meta/count", method = GET, produces
	 * = APPLICATION_JSON_VALUE) public ResponseEntity<ComponentData>
	 * getHelmMetaCount() {
	 * LOG.debug("Call Recieved @ //collector/helm/meta/count :: "); return
	 * ResponseEntity.ok(helmService.getHelmMetaCount()); }
	 * 
	 * @RequestMapping(value = "/collector/helm/meta/data", method = GET, produces =
	 * APPLICATION_JSON_VALUE) public ResponseEntity<ComponentData>
	 * getHelmMetaData() {
	 * LOG.debug("Call Recieved @ /collector/helm/meta/data :: "); return
	 * ResponseEntity.ok(helmService.getHelmMetaData()); }
	 * 
	 * @RequestMapping(value = "/collector/helm/container/processes/top", method =
	 * GET, produces = APPLICATION_JSON_VALUE) public ResponseEntity<ComponentData>
	 * getContainerProcessesTopRoute(@RequestParam(name = "containerId") String
	 * containerId) {
	 * LOG.debug("Call Recieved @ /collector/helm/container/processes/top :: ");
	 * return
	 * ResponseEntity.ok(helmService.getContainerProcessesTopRoute(containerId)); }
	 * 
	 * @RequestMapping(value = "/collector/helm/cpu/stats", method = GET, produces =
	 * APPLICATION_JSON_VALUE) public ResponseEntity<ComponentData>
	 * getHelmCpuStats() {
	 * LOG.debug("Call Recieved @ /collector/helm/cpu/stats :: "); return
	 * ResponseEntity.ok(helmService.getHelmCpuStats()); }
	 * 
	 * @RequestMapping(value = "/collector/helm/meta/aggregate", method = GET,
	 * produces = APPLICATION_JSON_VALUE) public ResponseEntity<ComponentData>
	 * getHelmMetaAggregate(@RequestParam String meta,
	 * 
	 * @RequestParam String status, @RequestParam String timeline, @RequestParam
	 * Integer range) { LOG.
	 * debug("Call Recieved @ /collector/helm/meta/aggregate :: Params - Workspace :"
	 * + meta + ", status: " + status + ", timeline" + timeline + ", range: " +
	 * range); return ResponseEntity.ok(helmService.getHelmMetaAggregate(meta,
	 * status, timeline, range)); }
	 * 
	 */}
