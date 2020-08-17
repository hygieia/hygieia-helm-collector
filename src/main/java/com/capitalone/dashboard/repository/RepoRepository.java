package com.capitalone.dashboard.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Repo;

public interface RepoRepository<T extends Repo> extends CrudRepository<T, ObjectId> {

	Repo findByName(String repo);
}
