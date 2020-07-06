package com.capitalone.dashboard.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Repo;

public interface RepoRepository<T extends Repo> extends CrudRepository<T, ObjectId> {

	@Query(value = "{'repo': ?0}")
	public Repo findByRepoName(String repo);

}
