
package com.capitalone.dashboard.collector;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.capitalone.dashboard.exception.CommandLineException;
import com.capitalone.dashboard.exception.NoDataFoundException;
import com.capitalone.dashboard.model.Chart;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Release;
import com.capitalone.dashboard.model.Repo;
import com.capitalone.dashboard.model.Version;
import com.capitalone.dashboard.repository.BaseCollectorRepository;
import com.capitalone.dashboard.repository.ChartRepository;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.HelmCollectorItemRepository;
import com.capitalone.dashboard.repository.ReleaseRepository;
import com.capitalone.dashboard.repository.RepoRepository;
import com.capitalone.dashboard.repository.VersionRepository;

/**
 * CollectorTask that fetches Helm(Networks, Containers, Nodes, Services, Nodes,
 * Tasks, CPUStats, Volume) details from Helm Daemon
 */
@Component
public class HelmCollectorTask extends CollectorTask<Collector> {
	private static final Log LOG = LogFactory.getLog(HelmCollectorTask.class);

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private final HelmCollectorItemRepository helmCollectoritemRepository;
	private final CollectorItemRepository collectoritemRepository;
	private final DefaultHelmClient helmClient;
	private final BaseCollectorRepository<Collector> baseCollectorRepository;
	private final VersionRepository<Version> versionRepository;
	private final ReleaseRepository<Release> releaseRepository;
	private final ChartRepository<Chart> chartRepository;
	private final RepoRepository<Repo> repoRepository;
	private final HelmSettings helmSetting;
	private Long timeout;
	private static String REGEX_NOT_REQUIRED = "Regex Not Required";

	@Autowired
	public HelmCollectorTask(TaskScheduler taskScheduler, BaseCollectorRepository<Collector> baseCollectorRepository,
			HelmCollectorItemRepository helmCollectoritemRepository, DefaultHelmClient helmClient,
			HelmSettings helmSetting, CollectorItemRepository collectoritemRepository,
			VersionRepository versionRepository, ReleaseRepository releaseRepository, ChartRepository chartRepository,
			RepoRepository repoRepository) {
		super(taskScheduler, "Helm");
		this.helmClient = helmClient;
		this.baseCollectorRepository = baseCollectorRepository;
		this.helmCollectoritemRepository = helmCollectoritemRepository;
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
		protoType.setCollectorType(CollectorType.Helm);
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

	@Override
	public void collect(Collector collector) {
		timeout = helmSetting.getHelmTimeout();

		try {
			if (helmSetting.getCheckVersion()) {
				getHelmVersion(helmSetting.getHelmTestCommand(), timeout);
			}

			List<Release> releases = (List<Release>) helmClient.getCommandResultComposed(helmSetting.getHelmListCmd(),
					helmSetting.getHelmListRegex(), timeout, Release.class);

			if (releases == null || releases.size() == 0) {
				// log no data
				LOG.error("No Release found, Command: " + helmSetting.getHelmListCmd() + ", Regex: "
						+ helmSetting.getHelmListRegex());
				throw new NoDataFoundException(helmSetting.getHelmListCmd());
			}

			releases.parallelStream().forEach(release -> {

				try {
					List<Chart> charts= null;;
					charts = (List<Chart>) helmClient.getCommandResultComposed(
							helmSetting.getHelmHistoryCommand() + " " + release.getName(),
							helmSetting.getHelmHistoryRegex(), timeout, Chart.class);
				

				if (charts == null || charts.size() == 0) {
					// If there is a release then there should be Charts
					throw new NoDataFoundException(helmSetting.getHelmHistoryCommand());
				}

				ObjectId releaseObjectId = saveOrUpdateRelease(release);

				removePreviousReleaseCycle(releaseObjectId);

				charts.parallelStream().forEach(chart -> {
					chart.setReleaseObjectId(releaseObjectId);

					saveOrUpdateChart(chart);
			
				});
				
				} catch (RuntimeException | IOException | InterruptedException | NoDataFoundException |  java.text.ParseException e) {
					// TODO Auto-generated catch block
					LOG.error("Command Execution issue found, Please check you Helm Installation; " + e.getMessage());
				}

			});

			List<Repo> repos = (List<Repo>) helmClient.getCommandResultComposed(helmSetting.getHelmRepoCommand(),
					helmSetting.getHelmRepoRegex(), timeout, Repo.class);

			if (repos == null || repos.size() == 0) {
				// log no data
				LOG.error("No Repo found, Command: ");
			}
			repos.parallelStream().forEach(repo -> saveOrUpdateRepo(repo));
		} catch (NoDataFoundException | CommandLineException | IOException | InterruptedException | java.text.ParseException e) {
			// TODO Auto-generated catch block
			LOG.error("Command Execution issue found, Please check you Helm Installation; " + e.getMessage());

		}

	}

	private void getHelmVersion(String command, Long timeout) throws CommandLineException, RuntimeException, IOException, InterruptedException {

		String versionOutput = (String) helmClient.getCommandResult(command, timeout);
		versionOutput = versionOutput.substring(versionOutput.indexOf('{'), versionOutput.indexOf('}') + 1);

		if (versionOutput == null || versionOutput.trim().isEmpty())
			throw new CommandLineException(command, REGEX_NOT_REQUIRED);

		JSONObject versionObj = helmClient.parseAsObject(versionOutput);

		saveOrUpdateVersion(versionObj);

	}

	private void removePreviousReleaseCycle(ObjectId releaseObjectId) {
		List<Chart> charts = chartRepository.findByReleaseId(releaseObjectId);
		chartRepository.delete(charts);

	}

	private ObjectId saveOrUpdateVersion(JSONObject jsonObj) {

		Version version = new Version();
		version.setGitCommit(jsonObj.get("GitCommit").toString());
		version.setGitTreeState(jsonObj.get("GitTreeState").toString());
		version.setGoVersion(jsonObj.get("GoVersion").toString());
		version.setVersion(jsonObj.get("Version").toString());

		versionRepository.deleteAll(); // We only keep the latest version
		return versionRepository.save(version).getId();
	}

	private ObjectId saveOrUpdateRelease(Release release) {

		// releaseRepository.findByReleaseName(release.getName())
		return releaseRepository.save(release).getId(); // flash : after saving always return primary id
	}

	private ObjectId saveOrUpdateRepo(Repo repo) {

		return repoRepository.save(repo).getId();
	}

	private ObjectId saveOrUpdateChart(Chart chart) {

		return chartRepository.save(chart).getId();
	}

}
