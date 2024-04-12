## commands used 💻🚀
Spring Boot Application Deployment with Docker, Minikube, and Helm
This document outlines the commands used to build, deploy, and access a Spring Boot application using Docker, Minikube, and Helm.

- Building and Running Locally:
```
./gradlew clean build
```

This command cleans any previous build artifacts and then builds your Spring Boot application using Gradle.
```
docker run -p 8080:8080 springboot-minikube
```

This command assumes you have a Docker image named springboot-minikube built from your application. It runs the image in a Docker container, mapping the container port 8080 to your host machine's port 8080. This allows you to access the application locally at http://localhost:8080.

- Setting Up Minikube and Helm:
```
brew install tree  # (For macOS users)
tree ytkubechart
```
These commands are relevant if you're on macOS. The first line installs the tree command-line tool, which helps visualize directory structures. The second line uses tree to display the directory structure of your Helm chart named ytkubechart.
```
minikube start --driver=docker
```
This command starts a Minikube cluster using the Docker driver. Minikube is a lightweight Kubernetes implementation that allows you to run Kubernetes locally for development and testing.

- Verifying Minikube Status:
```
kubectl get nodes
kubectl get services
```
These commands check the status of your Minikube cluster. The first one (kubectl get nodes) lists any running Kubernetes nodes (which should be your Minikube instance). The second one (kubectl get services) lists any existing Kubernetes services within the cluster.

- Building Docker Image and Pushing to Minikube:
```
eval $(minikube docker-env)
docker build -t springboot-minikube .
```
These commands assume you have Docker installed and running. The first line sets up the Docker environment for Minikube, allowing you to build and push images directly to the Minikube registry. The second line builds a Docker image tagged springboot-minikube using your Dockerfile in the current directory. This image is then pushed to the Minikube registry.

- Deploying the Application with Helm:
```
minikube image ls
helm create ytkubechart  # (if you don't have a chart already)
helm install mychart ytkubechart
```
These commands use Helm to deploy your application to Minikube. The first line (minikube image ls) lists images available in the Minikube registry (should include your springboot-minikube image).

The second line (helm create ytkubechart) is only necessary if you haven't created a Helm chart named ytkubechart before.
 This command initializes an empty Helm chart. You'll need to define the deployment information and references to your built image within this chart for successful deployment.

The third line (``helm install mychart ytkubechart``) installs your ``ytkubechart`` chart with the release name mychart. This deploys your application to the Minikube cluster.

- Verifying Deployment:
```
kubectl get pods
kubectl get services
minikube ip
```
These commands help verify if the deployment was successful. The first line (kubectl get pods) lists running pods in your Kubernetes cluster (should include a pod for your Spring Boot application). The second line (kubectl get services) lists Kubernetes services (should include a service for your application). The third line (minikube ip) displays the IP address of your Minikube cluster.

- Accessing the Application (Optional Methods):
```
minikube service mychart-ytkubechart --url
```
This command attempts to access your service through the Minikube service. However, this might not work yet because additional configuration (like Ingress) might be needed for external access.
```
kubectl get pods -o wide
```
This command displays detailed information about your pods, which can be helpful for troubleshooting deployment issues.

- Enabling Ingress and Upgrading Helm Chart (Optional):
```
minikube addons enable ingress
kubectl get pods -n ingress-nginx
helm upgrade mychart ytkubechart
```
These commands are optional and require additional configuration for external access through a domain name. The first line (minikube addons enable ingress) enables the Ingress addon in Minikube, which routes external traffic to your application. The second line (kubectl get pods -n ingress-nginx) checks if the Ingress controller pod is running in the ingress-nginx namespace.
 The third line (helm upgrade mychart ytkubechart) upgrades your existing Helm release

```
 ./gradlew clean build
 docker run -p 8080:8080 springboot-minikube
 helm create ytkubechart
 brew install tree
 tree ytkubechart
minikube start --driver=docker
kubectl get nodes
kubectl get services
eval $(minikube docker-env)
docker build -t springboot-minikube .
 minikube image ls
 helm install mychart ytkubechart
 kubectl get pods
 kubectl get services
 minikube ip
 minikube service mychart-ytkubechart --url
 kubectl get pods -o wide
 minikube addons enable ingress
 kubectl get pods -n ingress-nginx
helm upgrade mychart ytkubechart
 kubectl get ingress
sudo vi /etc/hosts add (127.0.0.1  ytlectures.com) inside config file
 minikube tunnel: to create tunnel
```

- Results of configuration
```
gboot-minikube kubectl get pods -n ingress-nginx
NAME                                        READY   STATUS      RESTARTS      AGE
ingress-nginx-admission-create-hvdzv        0/1     Completed   0             2d19h
ingress-nginx-admission-patch-49mfk         0/1     Completed   0             2d19h
ingress-nginx-controller-7c6974c4d8-h8lp6   1/1     Running     2 (56m ago)   2d19h
➜  springboot-minikube helm upgrade mychart ytkubechart
W0412 21:47:28.658752   82562 warnings.go:70] unknown field "spec.template.spec.containers[0].ports[0].httpGet"
Release "mychart" has been upgraded. Happy Helming!
NAME: mychart
LAST DEPLOYED: Fri Apr 12 21:47:28 2024
NAMESPACE: default
STATUS: deployed
REVISION: 2
NOTES:
1. Get the application URL by running these commands:
  http://ytlectures.com/
➜  springboot-minikube kubectl get ingress
NAME                  CLASS    HOSTS                ADDRESS        PORTS   AGE
mychart-ytkubechart   nginx    ytlectures.com       192.168.49.2   80      2m30s
nginx                 <none>   www.k8sfastapi.com   192.168.49.2   80      2d20h
➜  springboot-minikube sudo vi /etc/hosts
Password:
➜  springboot-minikube minikube tunnel
✅  Tunnel successfully started

📌  NOTE: Please do not close this terminal as this process must stay alive for the tunnel to be accessible ...

➜  springboot-minikube helm template ytkubechart
---
# Source: ytkubechart/templates/serviceaccount.yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: release-name-ytkubechart
  labels:
    helm.sh/chart: ytkubechart-0.1.2
    app.kubernetes.io/name: ytkubechart
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/version: "1.16.0"
    app.kubernetes.io/managed-by: Helm
automountServiceAccountToken: true
---
# Source: ytkubechart/templates/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: release-name-ytkubechart
  labels:
    helm.sh/chart: ytkubechart-0.1.2
    app.kubernetes.io/name: ytkubechart
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/version: "1.16.0"
    app.kubernetes.io/managed-by: Helm
spec:
  type: ClusterIP
  ports:
    - port: 8080
      targetPort: http
      protocol: TCP
      name: http
  selector:
    app.kubernetes.io/name: ytkubechart
    app.kubernetes.io/instance: release-name
---
# Source: ytkubechart/templates/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: release-name-ytkubechart
  labels:
    helm.sh/chart: ytkubechart-0.1.2
    app.kubernetes.io/name: ytkubechart
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/version: "1.16.0"
    app.kubernetes.io/managed-by: Helm
spec:
  replicas: 2
  selector:
    matchLabels:
      app.kubernetes.io/name: ytkubechart
      app.kubernetes.io/instance: release-name
  template:
    metadata:
      labels:
        helm.sh/chart: ytkubechart-0.1.2
        app.kubernetes.io/name: ytkubechart
        app.kubernetes.io/instance: release-name
        app.kubernetes.io/version: "1.16.0"
        app.kubernetes.io/managed-by: Helm
    spec:
      serviceAccountName: release-name-ytkubechart
      securityContext:
        {}
      containers:
        - name: ytkubechart
          securityContext:
            {}
          image: "springboot-minikube:latest"
          imagePullPolicy: IfNotPresent
          ports:
          - name: http
            containerPort: 8080
            protocol: TCP
          #livenessProbe:
           #
            httpGet:
              path: /
              port: http
         # readinessProbe:
           #
            httpGet:
              path: /
              port: http
          resources:
            {}
---
# Source: ytkubechart/templates/ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: release-name-ytkubechart
  labels:
    helm.sh/chart: ytkubechart-0.1.2
    app.kubernetes.io/name: ytkubechart
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/version: "1.16.0"
    app.kubernetes.io/managed-by: Helm
spec:
  rules:
    - host: "ytlectures.com"
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: release-name-ytkubechart
                port:
                  number: 8080
---
# Source: ytkubechart/templates/tests/test-connection.yaml
apiVersion: v1
kind: Pod
metadata:
  name: "release-name-ytkubechart-test-connection"
  labels:
    helm.sh/chart: ytkubechart-0.1.2
    app.kubernetes.io/name: ytkubechart
    app.kubernetes.io/instance: release-name
    app.kubernetes.io/version: "1.16.0"
    app.kubernetes.io/managed-by: Helm
  annotations:
    "helm.sh/hook": test
spec:
  containers:
    - name: wget
      image: busybox
      command: ['wget']
      args: ['release-name-ytkubechart:8080']
  restartPolicy: Never

```