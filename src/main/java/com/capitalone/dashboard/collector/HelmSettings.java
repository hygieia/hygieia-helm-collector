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
    private final String helmTestCommand = "helm version";
    private final String helmListCmd = "helm list";
    private final String helmHistoryCommand = "helm history";
    private final String helmRepoCommand = "helm repo list";
    private final String ocProjectsCommand = "oc projects";
    private final String ocLoginCommand = "oc login";
    private final String kubectlNamespacesCommand = "kubectl get namespaces";

    private String proxy;
    private String proxyPort;
    private String proxyUser;
    private String proxyPassword;

    @Value("${helm.cron}")
    private String cron;

    @Value("${helm.checkVersion: true}")
    private Boolean checkVersion;

    @Value("${helm.timeout:3000}")
    private Long helmTimeout;

    @Value("${helm.openshift:false}")
    private boolean isOpenshift;

    @Value("${helm.kubeConfig.path}")
    private String kubeConfigPath;

    @Value("${helm.openshift.token}")
    private String openshiftToken;

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

    public Long getHelmTimeout() {
        return helmTimeout;
    }

    public void setHelmTimeout(Long helmTimeout) {
        this.helmTimeout = helmTimeout;
    }

    public String getHelmListCmd() {
        return helmListCmd;
    }

    public String getHelmHistoryCommand() {
        return helmHistoryCommand;
    }

    public String getHelmRepoCommand() {
        return helmRepoCommand;
    }

    public boolean isOpenshift() {
        return isOpenshift;
    }

    public void setOpenshift(final boolean openshift) {
        isOpenshift = openshift;
    }

    public String getOcProjectsCommand() {
        return ocProjectsCommand;
    }

    public String getOcLoginCommand() {
        return ocLoginCommand;
    }

    public String getKubeConfigPath() {
        return kubeConfigPath;
    }

    public void setKubeConfigPath(final String kubeConfigPath) {
        this.kubeConfigPath = kubeConfigPath;
    }

    public String getOpenshiftToken() {
        return openshiftToken;
    }

    public void setOpenshiftToken(final String openshiftToken) {
        this.openshiftToken = openshiftToken;
    }

    public String getKubectlNamespacesCommand() {
        return kubectlNamespacesCommand;
    }
}
