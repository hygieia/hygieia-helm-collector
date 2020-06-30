package com.capitalone.dashboard.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Version;

public interface VersionRepository<T extends Version> extends CrudRepository<T, ObjectId> {

	

}
