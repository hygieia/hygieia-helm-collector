package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "helm_version")
public class Version {
	private String Version;
	private String GitCommit;
	private String GitTreeState;
	private String GoVersion;
	
	
	public Version() {
		// TODO Auto-generated constructor stub
	}


	public String getVersion() {
		return Version;
	}


	public void setVersion(String version) {
		Version = version;
	}


	public String getGitCommit() {
		return GitCommit;
	}


	public void setGitCommit(String gitCommit) {
		GitCommit = gitCommit;
	}


	public String getGitTreeState() {
		return GitTreeState;
	}


	public void setGitTreeState(String gitTreeState) {
		GitTreeState = gitTreeState;
	}


	public String getGoVersion() {
		return GoVersion;
	}


	public void setGoVersion(String goVersion) {
		GoVersion = goVersion;
	}
	
	
}
