package com.capitalone.dashboard.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Chart;

public interface ChartRepository<T extends Chart> extends CrudRepository<T, ObjectId> {

	@Query(value = "{'chart': ?0}")
	public Chart findByChartName(String chart);
	
	@Query(value = "{'releaseObjectId': ?0}")
	public List<Chart> findByReleaseId(ObjectId releaseObjectId);
	
	

}
