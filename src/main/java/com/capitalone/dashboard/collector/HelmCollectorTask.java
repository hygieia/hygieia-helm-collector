
package com.capitalone.dashboard.collector;

import static com.capitalone.dashboard.model.HelmCollectorItem.INSTANCE_PORT;
import static com.capitalone.dashboard.model.HelmCollectorItem.INSTANCE_URL;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.capitalone.dashboard.command.util.CommandLineUtil;
import com.capitalone.dashboard.exception.CommandLineException;
import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.CPUStats;
import com.capitalone.dashboard.model.CPUStats.CPUStatsUsage;
import com.capitalone.dashboard.model.CPUStats.MemoryStats;
import com.capitalone.dashboard.model.Chart;
import com.capitalone.dashboard.model.CollectionError;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Container;
import com.capitalone.dashboard.model.Container.Bridge;
import com.capitalone.dashboard.model.Container.Mount;
import com.capitalone.dashboard.model.Network;
import com.capitalone.dashboard.model.Node;
import com.capitalone.dashboard.model.Node.ManagerStatus;
import com.capitalone.dashboard.model.Node.Spec;
import com.capitalone.dashboard.model.Processes;
import com.capitalone.dashboard.model.Release;
import com.capitalone.dashboard.model.Task;
import com.capitalone.dashboard.model.Task.Status;
import com.capitalone.dashboard.model.Version;
import com.capitalone.dashboard.model.Volume;
import com.capitalone.dashboard.repository.BaseCollectorRepository;
import com.capitalone.dashboard.repository.CPUStatsRepository;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.repository.ContainerRepository;
import com.capitalone.dashboard.repository.HelmCollectorItemRepository;
import com.capitalone.dashboard.repository.ImageRepository;
import com.capitalone.dashboard.repository.NetworkRepository;
import com.capitalone.dashboard.repository.NodeRepository;
import com.capitalone.dashboard.repository.ProcessesRepository;
import com.capitalone.dashboard.repository.ServiceRepository;
import com.capitalone.dashboard.repository.TaskRepository;
import com.capitalone.dashboard.repository.VolumeRepository;

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
	private final HelmSettings helmSetting;
	private final ImageRepository imageRepository;
	private final NetworkRepository networkRepository;
	private final ServiceRepository serviceRepository;
	private final NodeRepository nodeRepository;
	private final ProcessesRepository processesRepository;
	private final TaskRepository taskRepository;
	private final VolumeRepository volumeRepository;
	private final ContainerRepository containerRepository;
	private final CPUStatsRepository cpuStatsRepository;
	private Long timeout;

	ResponseEntity<String> responseJSON;

	JSONParser jsonParser = new JSONParser();

	@Autowired
	public HelmCollectorTask(TaskScheduler taskScheduler, BaseCollectorRepository<Collector> baseCollectorRepository,
			HelmCollectorItemRepository helmCollectoritemRepository, DefaultHelmClient helmClient,
			HelmSettings helmSetting, ComponentRepository dbComponentRepository,
			CollectorItemRepository collectoritemRepository, ImageRepository imageRepository,
			NetworkRepository networkRepository, NodeRepository nodeRepository, ProcessesRepository processesRepository,
			 VolumeRepository volumeRepository, ContainerRepository containerRepository,
			 CPUStatsRepository cpuStatsRepository,TaskRepository taskRepository, ServiceRepository serviceRepository) {
		super(taskScheduler, "Helm");
		this.cpuStatsRepository = cpuStatsRepository;
		
		this.helmClient = helmClient;
		this.baseCollectorRepository = baseCollectorRepository;
		this.helmCollectoritemRepository = helmCollectoritemRepository;

		this.dbComponentRepository = dbComponentRepository;
		this.collectoritemRepository = collectoritemRepository;
		this.helmSetting = helmSetting;
		this.imageRepository = imageRepository;
		this.networkRepository = networkRepository;
		this.nodeRepository = nodeRepository;
		this.processesRepository = processesRepository;
		this.taskRepository = taskRepository;
		this.volumeRepository = volumeRepository;
		this.containerRepository = containerRepository;
		this.serviceRepository = serviceRepository;
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
	
	BufferedReader bufferedReader = null;
	String line;

	@Override
	public void collect(Collector collector) {

		timeout = helmSetting.getHelmTimeout();
		
		List<Release> releases =  helmClient.getCommandResultComposed(helmSetting.getHelmListCmd(), helmSetting.getHelmListRegex(), timeout, Release.class);
		
		if(release == null || releases.size() ==0)
			throw new CommandLineException("");
		
		releases.parallelStream().forEach(release -> {
			
			List<Chart> charts =  helmClient.getCommandResultComposed(helmSetting.getHelmHistoryCommand(), helmSetting.getHelmHistoryRegex(), timeout, Chart.class);
			
			charts.parallelStream().forEach(chart -> {
				saveOrUpdateChart(chart);
			});
			
		});
		
		
		if(charts == null || charts.size() ==0)
			throw new CommandLineException("");
		
		
		
		List<Release> releases =  helmClient.getCommandResultComposed(helmSetting.getHelmListCmd(), helmSetting.getHelmListRegex(), timeout, Release.class);
		
		if(release == null || releases.size() ==0)
			throw new CommandLineException("");
		
		
		
	}
	
	private void getHelmVersion() {
		String versionOutput = (String) helmClient.getCommandResult(helmSetting.getHelmTestCommand(), timeout);
		versionOutput = versionOutput.substring(versionOutput.indexOf('{'), versionOutput.indexOf('}') +1);
		
		JSONObject versionObj = helmClient.parseAsObject(versionOutput);
		
		if(versionObj == null)
			throw new CommandLineException("");
		
		
		
	}
	

	private void saveOrUpdateVersion(JSONObject jsonObj) {
		Version version = new Version();
		version.setGitCommit(jsonObj.get("GitCommit").toString());
		version.setGitTreeState(jsonObj.get("GitTreeState").toString());
		version.setGoVersion(jsonObj.get("GoVersion").toString());
		version.setVersion(jsonObj.get("Version").toString());
		
		
		versionRepository.removeAll(); // We only keep the latest version
		versionRepository.save(version);
	}
	
	
	private void saveOrUpdateRelease(Release release) {
		version 
	}
	
	private void saveOrUpdateRepo(Repo chart) {
		version 
	}
	
	
	private void saveOrUpdateChart(Chart version) {
		version 
	}
	
}
