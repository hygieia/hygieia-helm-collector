<h2>Helm Collector</h2>

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Gitter Chat](https://badges.gitter.im/Join%20Chat.svg)](https://www.apache.org/licenses/LICENSE-2.0)

A collector to showcase the Kubernetes Deployment Container details
<ul>
  <li>https://helm.sh/docs/intro/using_helm/</li>
  <li>https://helm.sh/docs/intro/quickstart/</li>
</ul>  

<h3>Which version of Helm it supports?</h3>
Helm-3

<h3>How is Helm Collector should is used?</h3>
This is a command line reader tool. This collector fires the command line command like helm ls , helm history and 
parse the output to find the chart deployment details in the clster
Thus this collector needs to deploy in the same machine where the helm -3 is installed


<h3>How is Helm App Organized?</h3>
The Helm tool is an standard tool for Kubernestes Deployment
This Helm 1.3 client is a client side tool and direclty talks to kubernetes with kube config file
This collector collects the deployment details of Releases, Charts & Repo
It simply fires the commandline command and collects/updates the Data in Mongo
The following commands it fires   

<h3>1. helm history [release_name]</h3>
https://helm.sh/docs/helm/helm_history/

<h4>Sample Output</h4>
NAME                UPDATED                     CHART
maudlin-arachnid    Mon May  9 16:07:08 2016    alpine-0.1.0

<h3>2. helm list </h3>
https://helm.sh/docs/helm/helm_list/

<h4>Sample Output</h4>
$ helm history angry-bird
REVISION    UPDATED                     STATUS          CHART             APP VERSION     DESCRIPTION
1           Mon Oct 3 10:15:13 2016     superseded      alpine-0.1.0      1.0             Initial install
2           Mon Oct 3 10:15:13 2016     superseded      alpine-0.1.0      1.0             Upgraded successfully
3           Mon Oct 3 10:15:13 2016     superseded      alpine-0.1.0      1.0             Rolled back to 2
4           Mon Oct 3 10:15:13 2016     deployed        alpine-0.1.0      1.0             Upgraded successfully

<h3>3. helm repo list</h3>
https://helm.sh/docs/helm/helm_repo_list/


Following are generaly used helm commands
helm repo add stable https://kubernetes-charts.storage.googleapis.com/
helm install testmysql  stable/mysql 
helm install testmysql  stable/mysql  --kubeconfig /home/ubuntu/.kube/config 


For Kubctl
curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl

kubectl --kubeconfig /home/ubuntu/.kube/config 
kubectl --kubeconfig /home/ubuntu/.kube/config get namespace
kubectl --kubeconfig /home/ubuntu/.kube/config get pods



Helm Repo Addition
helm repo add brigade https://brigadecore.github.io/charts
"brigade" has been added to your repositories

Sample Search
$ helm search repo brigade
NAME                        	CHART VERSION	APP VERSION	DESCRIPTION
brigade/brigade             	1.3.2        	v1.2.1     	Brigade provides event-driven scripting of Kube...
brigade/brigade-github-app  	0.4.1        	v0.2.1     	The Brigade GitHub App, an advanced gateway for...
brigade/brigade-github-oauth	0.2.0        	v0.20.0    	The legacy OAuth GitHub Gateway for Brigade
brigade/brigade-k8s-gateway 	0.1.0        	           	A Helm chart for Kubernetes
brigade/brigade-project     	1.0.0        	v1.0.0     	Create a Brigade project
brigade/kashti              	0.4.0        	v0.4.0     	A Helm chart for Kubernete



<----- **** ------>
