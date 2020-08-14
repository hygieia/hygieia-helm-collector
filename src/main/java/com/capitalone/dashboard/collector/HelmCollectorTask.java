
package com.capitalone.dashboard.collector;

import com.capitalone.dashboard.exception.CommandLineException;
import com.capitalone.dashboard.exception.NoDataFoundException;
import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CollectorTask that fetches Helm(Networks, Containers, Nodes, Services, Nodes,
 * Tasks, CPUStats, Volume) details from Helm Daemon
 */
@Component
public class HelmCollectorTask extends CollectorTask<Collector> {
    private static final Log LOG = LogFactory.getLog(HelmCollectorTask.class);

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

    @Autowired
    public HelmCollectorTask(TaskScheduler taskScheduler, BaseCollectorRepository<Collector> baseCollectorRepository,
                             HelmCollectorItemRepository helmCollectoritemRepository, DefaultHelmClient helmClient,
                             HelmSettings helmSetting, CollectorItemRepository collectoritemRepository,
                             VersionRepository<Version> versionRepository, ReleaseRepository<Release> releaseRepository, ChartRepository<Chart> chartRepository,
                             RepoRepository<Repo> repoRepository) {
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
            if (helmSetting.isOpenshift()) {
                authenticate();
                try {
                    String projectsResponse = helmClient.getCommandResult(constructCommand(helmSetting.getOcProjectsCommand(), "--config", helmSetting.getKubeConfigPath()), timeout);
                    final String[] resultLines = projectsResponse.split("\\r?\\n");
                    final List<String> projects = Arrays.stream(resultLines).skip(2).map(line -> {
                        final String[] subParts = line.split(" - ");
                        return subParts[0].replaceAll("\\* ", "").trim();
                    }).collect(Collectors.toList());
                    projects.removeIf(project -> project.startsWith("Using project") || project.isEmpty());
                    projects.parallelStream().forEach(project -> {
                        try {
                            process(project);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                String projectsResponse = helmClient.getCommandResult(helmSetting.getKubectlNamespacesCommand(), timeout);
                final String[] resultLines = projectsResponse.split("\\r?\\n");

                Arrays.stream(resultLines).skip(1).parallel().forEach(line -> {
                    final List<String> values = Arrays.stream(line.split("\\s+")).collect(Collectors.toList());
                    try {
                        process(values.get(0));
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (CommandLineException | IOException | InterruptedException e) {
            LOG.error("Command Execution issue found, Please check you Helm Installation; " + e.getMessage());
        }
    }

    private void process(final String project) throws IOException, InterruptedException {
        final List<? extends BaseModel> releasesCommandResult = helmClient.getCommandResultComposed(constructCommand(helmSetting.getHelmListCmd(), "-n", project, "--kubeconfig", helmSetting.getKubeConfigPath()), timeout, Release.class);
        final List<Release> releases = releasesCommandResult.stream().map(release -> (Release) release).collect(Collectors.toList());

        if (releases.isEmpty()) {
            LOG.error("No Release found, Command: " + constructCommand(helmSetting.getHelmListCmd(), "-n", project));
            return;
        }

        releases.parallelStream().forEach(release -> {
            try {
                final List<? extends BaseModel> chartsCommandResult = helmClient.getCommandResultComposed(constructCommand(helmSetting.getHelmHistoryCommand(), release.getName(), "-n", project, "--kubeconfig", helmSetting.getKubeConfigPath()), timeout, Chart.class);
                final List<Chart> charts = chartsCommandResult.stream().map(chart -> (Chart) chart).collect(Collectors.toList());
                if (charts.isEmpty()) {
                    // If there is a release then there should be Charts
                    throw new NoDataFoundException(helmSetting.getHelmHistoryCommand());
                }

                ObjectId releaseObjectId = saveOrUpdateRelease(release);
                removePreviousReleaseCycle(releaseObjectId);

                charts.parallelStream().forEach(chart -> {
                    chart.setReleaseObjectId(releaseObjectId);
                    saveOrUpdateChart(chart);
                });

            } catch (RuntimeException | IOException | InterruptedException | NoDataFoundException e) {
                LOG.error("Command Execution issue found, Please check you Helm Installation; " + e.getMessage());
            }
        });

        final List<? extends BaseModel> reposCommandResult = helmClient.getCommandResultComposed(helmSetting.getHelmRepoCommand(), timeout, Repo.class);
        final List<Repo> repos = reposCommandResult.stream().map(repo -> (Repo) repo).collect(Collectors.toList());
        if (repos.isEmpty()) {
            LOG.error("No Repo found, Command: " + helmSetting.getHelmRepoCommand());
            return;
        }
        repos.parallelStream().forEach(this::saveOrUpdateRepo);
    }

    private void getHelmVersion(String command, Long timeout) throws CommandLineException, RuntimeException, IOException, InterruptedException {

        String versionOutput = helmClient.getCommandResult(command, timeout);
        versionOutput = versionOutput.substring(versionOutput.indexOf('{'), versionOutput.indexOf('}') + 1);

        if (versionOutput.trim().isEmpty()) {
            throw new CommandLineException(command);
        }
        JSONObject versionObj = helmClient.parseAsObject(versionOutput);
        saveOrUpdateVersion(versionObj);
    }

    private void removePreviousReleaseCycle(ObjectId releaseObjectId) {
        List<Chart> charts = chartRepository.findByReleaseId(releaseObjectId);
        chartRepository.delete(charts);
    }

    private void saveOrUpdateVersion(JSONObject jsonObj) {
        Version version = new Version();
        version.setGitCommit(jsonObj.get("GitCommit").toString());
        version.setGitTreeState(jsonObj.get("GitTreeState").toString());
        version.setGoVersion(jsonObj.get("GoVersion").toString());
        version.setVersion(jsonObj.get("Version").toString());

        versionRepository.deleteAll(); // We only keep the latest version
        versionRepository.save(version).getId();
    }

    private ObjectId saveOrUpdateRelease(Release release) {
        final Release oldRelease = releaseRepository.findByNameAndNamespace(release.getName(), release.getNamespace());
        if (oldRelease != null) {
            release.setId(oldRelease.getId());
        }
        return releaseRepository.save(release).getId(); // flash : after saving always return primary id
    }

    private void saveOrUpdateRepo(Repo repo) {
        Repo oldRepo = repoRepository.findByName(repo.getName());
        if (oldRepo != null) {
            repo.setId(oldRepo.getId());
        }
        repoRepository.save(repo).getId();
    }

    private void saveOrUpdateChart(Chart chart) {
        chartRepository.save(chart).getId();
    }

    private void authenticate() {
        try {
            helmClient.getCommandResult(constructCommand(helmSetting.getOcLoginCommand(), "--token", helmSetting.getOpenshiftToken()), timeout);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String constructCommand(String... parts) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(parts).forEach(part -> stringBuilder.append(part).append(" "));
        return stringBuilder.toString().trim();
    }
}
