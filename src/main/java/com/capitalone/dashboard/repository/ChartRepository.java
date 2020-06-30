package com.capitalone.dashboard.repository;

import java.awt.Container;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Chart;

public interface ChartRepository<T extends Chart> extends CrudRepository<T, ObjectId> {

	@Query(value = "{'containerId': ?0}")
	public Chart findByChartName(String chart);

}
