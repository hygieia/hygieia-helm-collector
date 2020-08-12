package com.capitalone.dashboard.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitalone.dashboard.model.Chart;
import com.capitalone.dashboard.model.ComponentData;
import com.capitalone.dashboard.model.Release;
import com.capitalone.dashboard.model.Repo;
import com.capitalone.dashboard.repository.ChartRepository;
import com.capitalone.dashboard.repository.ReleaseRepository;
import com.capitalone.dashboard.repository.RepoRepository;

@Service
public class HelmServiceImpl implements HelmService {
	private static final Log LOG = LogFactory.getLog(HelmService.class);

	@Autowired
	ReleaseRepository<Release> releaseRepository;
	
	
	@Autowired
	ChartRepository<Chart> chartRepository;
	
	
	@Autowired
	RepoRepository<Repo> repoRepository;
	
	@Override
	public ComponentData getRelease() {
		// TODO Auto-generated method stub
		ComponentData componentData = new ComponentData();
		componentData.setData(releaseRepository.findAll());
		return componentData;
	}

	@Override
	public ComponentData getChart(String releaseId) {
		// TODO Auto-generated method stub
		ComponentData componentData = new ComponentData();
		 componentData.setData(chartRepository.findByReleaseId(new ObjectId(releaseId)));
		 return componentData;
	}

	@Override
	public ComponentData getRepo() {
		ComponentData componentData = new ComponentData();
		// TODO Auto-generated method stub
		 componentData.setData(repoRepository.findAll());
		 return componentData;

	}

	 
	 
}
