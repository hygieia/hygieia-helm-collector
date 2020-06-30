package com.capitalone.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.capitalone.dashboard.collector.HelmSettings;
import com.capitalone.dashboard.model.CPUStats;
import com.capitalone.dashboard.model.ComponentData;
import com.capitalone.dashboard.model.Container;
import com.capitalone.dashboard.model.Image;
import com.capitalone.dashboard.model.Network;
import com.capitalone.dashboard.model.Node;
import com.capitalone.dashboard.model.Processes;
import com.capitalone.dashboard.model.Series;
import com.capitalone.dashboard.model.Task;
import com.capitalone.dashboard.model.Volume;
import com.capitalone.dashboard.repository.CPUStatsRepository;
import com.capitalone.dashboard.repository.ContainerRepository;
import com.capitalone.dashboard.repository.ImageRepository;
import com.capitalone.dashboard.repository.NetworkRepository;
import com.capitalone.dashboard.repository.NodeRepository;
import com.capitalone.dashboard.repository.ProcessesRepository;
import com.capitalone.dashboard.repository.TaskRepository;
import com.capitalone.dashboard.repository.VolumeRepository;

@Service
public class HelmServiceImpl implements HelmService {
	private static final Log LOG = LogFactory.getLog(HelmService.class);

	@Autowired
	NetworkRepository<Network> networkRepository;

	@Autowired
	NodeRepository<Node> nodeRepository;

	@Autowired
	ContainerRepository<Container> containerRepository;
	
	@Autowired
	ImageRepository<Image> imageRepository;

	@Autowired
	ProcessesRepository<Processes> processesRepository;

	@Autowired
	TaskRepository<Task> taskRepository;

	@Autowired
	VolumeRepository<Volume> volumeRepository;

	@Autowired
	CPUStatsRepository<CPUStats> cpuStatsRepository;

	@Autowired
	HelmSettings dockerSettings;

	private boolean isConfigSet() {
		// return collectorItemRepository.findAll().iterator().hasNext();
		return false;
	}

	@Override
	public ComponentData getDockerMetaCount() {
		// TODO Auto-generated method stub
		CrudRepository[] obj = { networkRepository, nodeRepository, containerRepository, imageRepository,
				processesRepository, taskRepository };
		String[] names = { "Network", "Node", "Container", "Image", "Processes", "Task" };
		int i = 0;
		ComponentData componentData = new ComponentData();
		List<Series> data = new ArrayList<Series>();
		for (CrudRepository o : obj) {
			Series series = new Series();
			series.setName(names[i++]);
			series.setValue(o.count());

			data.add(series);
		}

		componentData.setData(data);
		return componentData;
	}
	
	public ComponentData getDockerMetaAggregate(String meta, String status, String timeline, Integer range) {
		ComponentData componentData = new ComponentData();

		return componentData;
	}
	
	public ComponentData getDockerMetaData() {
		ComponentData componentData = new ComponentData();
		
		return componentData;
	}
	
	public ComponentData getContainerProcessesTopRoute(String containerId) {
		ComponentData componentData = new ComponentData();

		return componentData;
	}
	
	public ComponentData getDockerCpuStats() {
		ComponentData componentData = new ComponentData();
		 		return componentData;
	}

}
