package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.Release;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface ReleaseRepository<T extends Release> extends CrudRepository<T, ObjectId> {

	Release findByNameAndNamespace(String name, String namespace);
}
