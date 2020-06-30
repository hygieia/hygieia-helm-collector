https://helm.sh/docs/helm/helm_history/


https://helm.sh/docs/intro/using_helm/


https://helm.sh/docs/helm/helm_show_chart/


https://helm.sh/docs/intro/quickstart/

helm repo add stable https://kubernetes-charts.storage.googleapis.com/


root@ip-172-31-21-251:/home/ubuntu# helm install testmysql  stable/mysql 
Error: Kubernetes cluster unreachable


root@ip-172-31-21-251:/home/ubuntu# helm install testmysql  stable/mysql  --kubeconfig /home/ubuntu/.kube/config 


FOr Kubctl
curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl


ll


kubectl --kubeconfig /home/ubuntu/.kube/config 


kubectl --kubeconfig /home/ubuntu/.kube/config get namespace


kubectl --kubeconfig /home/ubuntu/.kube/config get pods


cd /usr/local/bin/



helm repo add brigade https://brigadecore.github.io/charts
"brigade" has been added to your repositories
$ helm search repo brigade
NAME                        	CHART VERSION	APP VERSION	DESCRIPTION
brigade/brigade             	1.3.2        	v1.2.1     	Brigade provides event-driven scripting of Kube...
brigade/brigade-github-app  	0.4.1        	v0.2.1     	The Brigade GitHub App, an advanced gateway for...
brigade/brigade-github-oauth	0.2.0        	v0.20.0    	The legacy OAuth GitHub Gateway for Brigade
brigade/brigade-k8s-gateway 	0.1.0        	           	A Helm chart for Kubernetes
brigade/brigade-project     	1.0.0        	v1.0.0     	Create a Brigade project
brigade/kashti              	0.4.0        	v0.4.0     	A Helm chart for Kubernetes



