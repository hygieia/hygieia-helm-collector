
package com.capitalone.dashboard.collector;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.capitalone.dashboard.exception.CommandLineException;
import com.capitalone.dashboard.model.Chart;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Release;
import com.capitalone.dashboard.model.Repo;
import com.capitalone.dashboard.model.Version;
import com.capitalone.dashboard.repository.BaseCollectorRepository;
import com.capitalone.dashboard.repository.ChartRepository;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.repository.HelmCollectorItemRepository;
import com.capitalone.dashboard.repository.ReleaseRepository;
import com.capitalone.dashboard.repository.RepoRepository;
import com.capitalone.dashboard.repository.VersionRepository;

/**
 * CollectorTask that fetches Helm(Networks, Containers, Nodes, Services, Nodes, Tasks, CPUStats, Volume) details
 * from Helm Daemon
 */
@Component
public class HelmCollectorTask extends CollectorTask<Collector> {
	private static final Log LOG = LogFactory.getLog(HelmCollectorTask.class);

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private final HelmCollectorItemRepository helmCollectoritemRepository;
	private final CollectorItemRepository collectoritemRepository;
	private final DefaultHelmClient helmClient;
	private final ComponentRepository dbComponentRepository;
	private final BaseCollectorRepository<Collector> baseCollectorRepository;
	VersionRepository versionRepository; 
	ReleaseRepository releaseRepository; 
	ChartRepository chartRepository; 
	RepoRepository repoRepository;
	 final HelmSettings helmSetting;
	private Long timeout;

	ResponseEntity<String> responseJSON;

	JSONParser jsonParser = new JSONParser();

	@Autowired
	public HelmCollectorTask(TaskScheduler taskScheduler, BaseCollectorRepository<Collector> baseCollectorRepository,
			HelmCollectorItemRepository helmCollectoritemRepository, DefaultHelmClient helmClient,
			HelmSettings helmSetting, ComponentRepository dbComponentRepository,
			CollectorItemRepository collectoritemRepository, VersionRepository versionRepository, 
			ReleaseRepository releaseRepository, ChartRepository chartRepository, 
			RepoRepository repoRepository ) {
		super(taskScheduler, "Helm");
		this.helmClient = helmClient;
		this.baseCollectorRepository = baseCollectorRepository;
		this.helmCollectoritemRepository = helmCollectoritemRepository;

		this.dbComponentRepository = dbComponentRepository;
		this.collectoritemRepository = collectoritemRepository;
		this.helmSetting = helmSetting;
		this.versionRepository = versionRepository;
		this.releaseRepository = releaseRepository;
		this.chartRepository = chartRepository;
		this.repoRepository = repoRepository;
	}

	@Override
	public Collector getCollector() { 
		Collector protoType = new Collector();
		protoType.setName("Helm"); // Invoked as the frequency for cron
		//protoType.setCollectorType(CollectorType.Helm);
		protoType.setOnline(true);
		protoType.setEnabled(true);

		Map<String, Object> allOptions = new HashMap<>();
		protoType.setAllFields(allOptions);

		Map<String, Object> uniqueOptions = new HashMap<>();
		protoType.setUniqueFields(uniqueOptions);

		return protoType;
	}

	@Override
	public BaseCollectorRepository<Collector> getCollectorRepository() {
		return baseCollectorRepository;
	}

	@Override
	public String getCron() {

		return helmSetting.getCron();
	}
	
	BufferedReader bufferedReader = null;
	String line;

	@Override
	public void collect(Collector collector) {
		/*
		 * 
		 * timeout = helmSetting.getHelmTimeout();
		 * 
		 * List<Release> releases = (List<Release>)
		 * helmClient.getCommandResultComposed(helmSetting.getHelmListCmd(),
		 * helmSetting.getHelmListRegex(), timeout, Release.class);
		 * 
		 * if(releases == null || releases.size() ==0) throw new
		 * CommandLineException("");
		 * 
		 * releases.parallelStream().forEach(release -> {
		 * 
		 * saveOrUpdateRelease(release);
		 * 
		 * List<Chart> charts =
		 * helmClient.getCommandResultComposed(helmSetting.getHelmHistoryCommand(),
		 * helmSetting.getHelmHistoryRegex(), timeout, Chart.class);
		 * 
		 * if(charts == null || charts.size() ==0) throw new CommandLineException("");
		 * 
		 * charts.parallelStream().forEach(chart -> { saveOrUpdateChart(chart); }); });
		 * 
		 * List<Repo> repos = (List<Repo>)
		 * helmClient.getCommandResultComposed(helmSetting.getHelmListCmd(),
		 * helmSetting.getHelmListRegex(), timeout, Release.class);
		 * 
		 * if(repos == null || repos.size() ==0) throw new CommandLineException("");
		 */}
	
	private void getHelmVersion() {
		/*
		 * String versionOutput = (String)
		 * helmClient.getCommandResult(helmSetting.getHelmTestCommand(), timeout);
		 * versionOutput = versionOutput.substring(versionOutput.indexOf('{'),
		 * versionOutput.indexOf('}') +1);
		 * 
		 * JSONObject versionObj = helmClient.parseAsObject(versionOutput);
		 * 
		 * if(versionObj == null) throw new CommandLineException("");
		 */}
	

	private void saveOrUpdateVersion(JSONObject jsonObj) {
		/*
		 * Version version = new Version();
		 * version.setGitCommit(jsonObj.get("GitCommit").toString());
		 * version.setGitTreeState(jsonObj.get("GitTreeState").toString());
		 * version.setGoVersion(jsonObj.get("GoVersion").toString());
		 * version.setVersion(jsonObj.get("Version").toString());
		 * 
		 * 
		 * versionRepository.removeAll(); // We only keep the latest version
		 * versionRepository.save(version);
		 */}
	
	
	private void saveOrUpdateRelease(Release release) {
		/*
		 * releaseRepository.save(release);
		 */}
	
	private void saveOrUpdateRepo(Repo repo) {
		/*
		 * repoRepository.save(repo);
		 */}
	
	
	private void saveOrUpdateChart(Chart chart) {
		/*
		 * chartRepository.save(chart);
		 */}
	
}
