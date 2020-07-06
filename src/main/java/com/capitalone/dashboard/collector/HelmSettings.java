package com.capitalone.dashboard.collector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Bean to hold settings specific to the Docker collector.
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "helm")
public class HelmSettings {
	private String proxy;
	private String proxyPort;
	private String proxyUser;
	private String proxyPassword;
	
	@Value("${helm.cron}")
	private String cron;
	
	@Value("${helm.checkVersion: true}")
	private Boolean checkVersion;
	
	@Value("${helm.test:helm version}")
	private String helmTestCommand;

	@Value("{$helm.timeout:3000}")
	private Long helmTimeout;
	
	@Value("{$helm.command.list}")
	private String helmListCmd;
	
	@Value("{$helm.list.regex}")
	private String helmListRegex;
	
	@Value("{$helm.history.command:helm history}")
	private String helmHistoryCommand;
	
	
	@Value("{$helm.history.regex}")
	private String helmHistoryRegex;
	
	
	@Value("{$helm.repo.command:helm repo}")
	private String helmRepoCommand;
	
	
	@Value("{$helm.repo.command:helm repo}")
	private String helmRepoRegex;


	
	
	
	public Boolean getCheckVersion() {
		return checkVersion;
	}


	public void setCheckVersion(Boolean checkVersion) {
		this.checkVersion = checkVersion;
	}


	public String getProxy() {
		return proxy;
	}


	public void setProxy(String proxy) {
		this.proxy = proxy;
	}


	public String getProxyPort() {
		return proxyPort;
	}


	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}


	public String getProxyUser() {
		return proxyUser;
	}


	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}


	public String getProxyPassword() {
		return proxyPassword;
	}


	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}


	public String getCron() {
		return cron;
	}


	public void setCron(String cron) {
		this.cron = cron;
	}


	public String getHelmTestCommand() {
		return helmTestCommand;
	}


	public void setHelmTestCommand(String helmTestCommand) {
		this.helmTestCommand = helmTestCommand;
	}


	public Long getHelmTimeout() {
		return helmTimeout;
	}


	public void setHelmTimeout(Long helmTimeout) {
		this.helmTimeout = helmTimeout;
	}


	public String getHelmListCmd() {
		return helmListCmd;
	}


	public void setHelmListCmd(String helmListCmd) {
		this.helmListCmd = helmListCmd;
	}


	public String getHelmListRegex() {
		return helmListRegex;
	}


	public void setHelmListRegex(String helmListRegex) {
		this.helmListRegex = helmListRegex;
	}


	public String getHelmHistoryCommand() {
		return helmHistoryCommand;
	}


	public void setHelmHistoryCommand(String helmHistoryCommand) {
		this.helmHistoryCommand = helmHistoryCommand;
	}


	public String getHelmHistoryRegex() {
		return helmHistoryRegex;
	}


	public void setHelmHistoryRegex(String helmHistoryRegex) {
		this.helmHistoryRegex = helmHistoryRegex;
	}


	public String getHelmRepoCommand() {
		return helmRepoCommand;
	}


	public void setHelmRepoCommand(String helmRepoCommand) {
		this.helmRepoCommand = helmRepoCommand;
	}


	public String getHelmRepoRegex() {
		return helmRepoRegex;
	}


	public void setHelmRepoRegex(String helmRepoRegex) {
		this.helmRepoRegex = helmRepoRegex;
	}
	
	
}
