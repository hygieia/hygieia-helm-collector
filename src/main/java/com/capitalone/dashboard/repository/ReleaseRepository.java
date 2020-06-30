package com.capitalone.dashboard.repository;

import java.awt.Container;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Release;

public interface ReleaseRepository<T extends Release> extends CrudRepository<T, ObjectId> {

	@Query(value = "{'release': ?0}")
	public Release findByReleaseName(String release);

}
