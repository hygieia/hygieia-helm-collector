package com.capitalone.dashboard.repository;

import java.awt.Container;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Repo;

public interface RepoRepository<T extends Container> extends CrudRepository<T, ObjectId> {

	@Query(value = "{'repo': ?0}")
	public Repo findByRepoName(String repo);

}
