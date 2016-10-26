# AppStash - Microservice Phone Shop Application

This example shows how to run the microservice phone shop application with Kubernetes. Kubernetes is an open source
system for managing containerized applications across multiple hosts, providing basic mechanisms for deployment,
maintenance, and scaling of applications.

![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/microservice-appserver.png)

## Install

If you want to run the example Microservice Phone Shop Application, you have to install the components which are
described in the [root readme](https://github.com/zutherb/AppStash#running) and the following components:

```
brew install kubernetes-cli wget fleetctl etcdctl
submodule init
submodule update
```

Now you should be able to init a Kubernetes cluster. The cluster is based on [CoreOS](https://coreos.com/) which is
a new Linux distribution that has been rearchitected to provide features needed to run modern infrastructure stacks.
Current ```Vagrantfile``` will bootstrap one VM with everything it needs to become a Kubernetes master.

```
cd vagrant
vagrant up master
```

Verify that ```fleet``` sees it. [fleet](https://github.com/coreos/fleet) ties together [systemd](http://coreos.com/using-coreos/systemd) and [etcd](https://github.com/coreos/etcd)
into a distributed init system. Think of it as an extension of systemd that operates at the cluster level instead of the
machine level. This project is very low level and is designed as a foundation for higher order orchestration.

```
fleetctl list-machines
```

You should see something like

```
MACHINE     IP      METADATA
dd0ee115... 172.17.8.101    role=master
```

Current Vagrantfile will bootstrap two VMs, by default, with everything needed to have two Kubernetes minions. You can
change this by setting the NUM_INSTANCES environment variable (explained below).

```
NODE_MEM=1536 NODE_CPUS=1 NUM_INSTANCES=3 vagrant up
```

If everything is init you has to export the following environment variables.

```
export ETCDCTL_PEERS=http://172.17.8.101:4001
export FLEETCTL_ENDPOINT=http://172.17.8.101:4001
export KUBERNETES_MASTER=http://172.17.8.101:8080
```

## Kubernetes User Interface

Kubernetes currently supports a simple [web user interface](https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/ui.md).

![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/kubernetes-ui.png)


Start the server:

```
kubectl proxy --www=$PWD/src/www
```

The UI should now be running on [localhost](http://localhost:8001/static/).

## Run

You can use the cluster after a while the cluster should be initialized. If the following command show the same output,
our cluster is ready.

```
kubectl get pods
POD                 IP                  CONTAINER(S)        IMAGE(S)            HOST                LABELS              STATUS
```

Now you can deploy the microservice phone shop application. Therefore you has to change to the ```example/shop``` folder
and execute the ```deploy_cluster.sh```

```
cd example/shop
./deploy_cluster.sh
*********************************
Setup Pods
*********************************
redis
cart
mongodb
product
navigation
shop
catalog
*********************************
Setup Replication Controller
*********************************
cart
product
navigation
catalog
*********************************
Setup Services
*********************************
redis
cart
mongodb
product
navigation
shop
catalog
```

Now it takes a while again, because all docker images has to be loaded from the docker registry. Unfortunately the
cluster is not very chatty. Please the following [wikipage](https://github.com/GoogleCloudPlatform/kubernetes/wiki/Debugging-FAQ)
for more information, how to debug the cluster. If pods stay pending, you can use ```kubectl describe pod podname```
for describing the last thing that happened to the pod. Furthermore you can use ```kubectl get events``` to see all
cluster events.

```
kubectl get events
TIME                              NAME                KIND                SUBOBJECT                           REASON              SOURCE                   MESSAGE
Sat, 14 Mar 2015 08:17:51 +0000   mongodb             Pod                                                     scheduled           {scheduler }             Successfully assigned mongodb to 172.17.8.102
Sat, 14 Mar 2015 08:17:51 +0000   cart                Pod                                                     scheduled           {scheduler }             Successfully assigned cart to 172.17.8.103
Sat, 14 Mar 2015 08:18:03 +0000   cart-fplln          BoundPod            implicitly required container POD   pulled              {kubelet 172.17.8.102}   Successfully pulled image "kubernetes/pause:latest"
Sat, 14 Mar 2015 08:18:03 +0000   shop                BoundPod            implicitly required container POD   created             {kubelet 172.17.8.104}   Created with docker id bfeb6e381f537c1f1825331fc9a4dba49baa497fb4d9e105d9c4048e8c69f538
Sat, 14 Mar 2015 08:18:03 +0000   shop                BoundPod            implicitly required container POD   pulled              {kubelet 172.17.8.104}   Successfully pulled image "kubernetes/pause:latest"
Sat, 14 Mar 2015 08:28:09 +0000   catalog             BoundPod            spec.containers{catalog}            started             {kubelet 172.17.8.104}   Started with docker id 47b19b30e00d6be63cb9d5f04deb53615bfbc4c25692bd3e12a0af4dc63f9c4c
Sat, 14 Mar 2015 08:28:09 +0000   catalog             BoundPod            spec.containers{catalog}            created             {kubelet 172.17.8.104}   Created with docker id 47b19b30e00d6be63cb9d5f04deb53615bfbc4c25692bd3e12a0af4dc63f9c4c

kubectl describe pod cart
Name:				cart
Image(s):			zutherb/cart-service
Host:				172.17.8.103/172.17.8.103
Labels:				name=cart,role=service
Status:				Running
Replication Controllers:	cart (2/2 replicas created)
Events:
Time				            From			        SubobjectPath				        Reason		Message
Sat, 14 Mar 2015 08:17:51 +0000	{scheduler }						                        scheduled	Successfully assigned cart to 172.17.8.103
Sat, 14 Mar 2015 08:18:03 +0000	{kubelet 172.17.8.103}	implicitly required container POD	pulled		Successfully pulled image "kubernetes/pause:latest"
Sat, 14 Mar 2015 08:18:04 +0000	{kubelet 172.17.8.103}	implicitly required container POD	created		Created with docker id cdf3ec57889e4b3cb9feb76178f7e78fefdfe4e53a033c2808e49abf792b21ad
Sat, 14 Mar 2015 08:18:04 +0000	{kubelet 172.17.8.103}	implicitly required container POD	started		Started with docker id cdf3ec57889e4b3cb9feb76178f7e78fefdfe4e53a033c2808e49abf792b21ad
Sat, 14 Mar 2015 08:33:10 +0000	{kubelet 172.17.8.103}	spec.containers{cart}			    pulled		Successfully pulled image "zutherb/cart-service"
Sat, 14 Mar 2015 08:33:10 +0000	{kubelet 172.17.8.103}	spec.containers{cart}			    created		Created with docker id 692e0919e4c0da15b685cafcaf3a07addf5855dae9ed3be2ee0367da3ab82026
Sat, 14 Mar 2015 08:33:10 +0000	{kubelet 172.17.8.103}	spec.containers{cart}			    started		Started with docker id 692e0919e4c0da15b685cafcaf3a07addf5855dae9ed3be2ee0367da3ab82026
```

You should see that all pods are running after a while.

```
kubectl get pods
POD                 IP                  CONTAINER(S)        IMAGE(S)                     HOST                        LABELS                                           STATUS
cart                10.244.102.5        cart                zutherb/cart-service         172.17.8.103/172.17.8.103   name=cart,role=service                           Running
cart-fplln          10.244.5.5          cart                zutherb/cart-service         172.17.8.102/172.17.8.102   name=cart,uses=redis                             Running
catalog             10.244.59.2         catalog             zutherb/catalog-frontend     172.17.8.104/172.17.8.104   name=catalog,role=frontend                       Running
catalog-0133o       10.244.5.3          catalog             zutherb/catalog-frontend     172.17.8.102/172.17.8.102   name=catalog,uses=product,navigation,cart,shop   Running
mongodb             10.244.5.2          mongodb             dockerfile/mongodb           172.17.8.102/172.17.8.102   name=mongodb,role=database                       Running
navigation          10.244.102.3        navigation          zutherb/navigation-service   172.17.8.103/172.17.8.103   name=navigation,role=service                     Running
navigation-oh43e    10.244.102.2        navigation          zutherb/navigation-service   172.17.8.103/172.17.8.103   name=navigation,role=backend,uses=mongodb        Running
product             10.244.5.4          product             zutherb/product-service      172.17.8.102/172.17.8.102   name=product,role=service                        Running
product-gziey       10.244.102.4        product             zutherb/product-service      172.17.8.103/172.17.8.103   name=product,role=backend,uses=mongodb           Running
redis               10.244.59.4         redis               dockerfile/redis             172.17.8.104/172.17.8.104   name=redis,role=database                         Running
shop                10.244.59.3         shop                zutherb/monolithic-shop      172.17.8.104/172.17.8.104   name=shop,role=frontend,uses=mongodb,cart        Running
```

Now you can work with the cluster, e.g. you can resize a replication controller or deploy a a/b test.

```
kubectl resize rc catalog --replicas=3
resized
kubectl get pods
POD                 IP                  CONTAINER(S)        IMAGE(S)                     HOST                        LABELS                                           STATUS
cart                10.244.102.5        cart                zutherb/cart-service         172.17.8.103/172.17.8.103   name=cart,role=service                           Running
cart-fplln          10.244.5.5          cart                zutherb/cart-service         172.17.8.102/172.17.8.102   name=cart,uses=redis                             Running
catalog             10.244.59.2         catalog             zutherb/catalog-frontend     172.17.8.104/172.17.8.104   name=catalog,role=frontend                       Running
catalog-0133o       10.244.5.3          catalog             zutherb/catalog-frontend     172.17.8.102/172.17.8.102   name=catalog,uses=product,navigation,cart,shop   Running
catalog-ls6k1       10.244.102.6        catalog             zutherb/catalog-frontend     172.17.8.103/172.17.8.103   name=catalog,uses=product,navigation,cart,shop   Running
mongodb             10.244.5.2          mongodb             dockerfile/mongodb           172.17.8.102/172.17.8.102   name=mongodb,role=database                       Running
navigation          10.244.102.3        navigation          zutherb/navigation-service   172.17.8.103/172.17.8.103   name=navigation,role=service                     Running
navigation-oh43e    10.244.102.2        navigation          zutherb/navigation-service   172.17.8.103/172.17.8.103   name=navigation,role=backend,uses=mongodb        Running
product             10.244.5.4          product             zutherb/product-service      172.17.8.102/172.17.8.102   name=product,role=service                        Running
product-gziey       10.244.102.4        product             zutherb/product-service      172.17.8.103/172.17.8.103   name=product,role=backend,uses=mongodb           Running
redis               10.244.59.4         redis               dockerfile/redis             172.17.8.104/172.17.8.104   name=redis,role=database                         Running
shop                10.244.59.3         shop                zutherb/monolithic-shop      172.17.8.104/172.17.8.104   name=shop,role=frontend,uses=mongodb,cart        Running

kubectl resize rc catalog --replicas=2
resized
kubectl create -f catalog-b.json
catalog-b
kubectl get pods
POD                 IP                  CONTAINER(S)        IMAGE(S)                     HOST                        LABELS                                           STATUS
cart                10.244.102.5        cart                zutherb/cart-service         172.17.8.103/172.17.8.103   name=cart,role=service                           Running
cart-fplln          10.244.5.5          cart                zutherb/cart-service         172.17.8.102/172.17.8.102   name=cart,uses=redis                             Running
catalog-0133o       10.244.5.3          catalog             zutherb/catalog-frontend     172.17.8.102/172.17.8.102   name=catalog,uses=product,navigation,cart,shop   Running
catalog-b           10.244.59.5         catalog-b           zutherb/catalog-frontend     172.17.8.104/172.17.8.104   name=catalog-b,role=frontend                     Running
catalog-ls6k1       10.244.102.6        catalog             zutherb/catalog-frontend     172.17.8.103/172.17.8.103   name=catalog,uses=product,navigation,cart,shop   Running
mongodb             10.244.5.2          mongodb             dockerfile/mongodb           172.17.8.102/172.17.8.102   name=mongodb,role=database                       Running
navigation          10.244.102.3        navigation          zutherb/navigation-service   172.17.8.103/172.17.8.103   name=navigation,role=service                     Running
navigation-oh43e    10.244.102.2        navigation          zutherb/navigation-service   172.17.8.103/172.17.8.103   name=navigation,role=backend,uses=mongodb        Running
product             10.244.5.4          product             zutherb/product-service      172.17.8.102/172.17.8.102   name=product,role=service                        Running
product-gziey       10.244.102.4        product             zutherb/product-service      172.17.8.103/172.17.8.103   name=product,role=backend,uses=mongodb           Running
redis               10.244.59.4         redis               dockerfile/redis             172.17.8.104/172.17.8.104   name=redis,role=database                         Running
shop                10.244.59.3         shop                zutherb/monolithic-shop      172.17.8.104/172.17.8.104   name=shop,role=frontend,uses=mongodb,cart        Running

open http://172.17.8.104
```

You can the the complete example in the following video.

[![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/microservice-appserver-youtube.png)](https://www.youtube.com/watch?v=C7TyIf1GqgI)

## Updating the cluster

```
kubectl resize rc catalog --replicas=3
resized
kubectl delete pod catalog-b
catalog-b
kubectl get pods
POD                 IP                  CONTAINER(S)        IMAGE(S)                     HOST                        LABELS                                           STATUS
cart                10.244.102.5        cart                zutherb/cart-service         172.17.8.103/172.17.8.103   name=cart,role=service                           Running
cart-fplln          10.244.5.5          cart                zutherb/cart-service         172.17.8.102/172.17.8.102   name=cart,uses=redis                             Running
catalog-0133o       10.244.5.3          catalog             zutherb/catalog-frontend     172.17.8.102/172.17.8.102   name=catalog,uses=product,navigation,cart,shop   Running
catalog-hh2gd       10.244.59.6         catalog             zutherb/catalog-frontend     172.17.8.104/172.17.8.104   name=catalog,uses=product,navigation,cart,shop   Running
catalog-ls6k1       10.244.102.6        catalog             zutherb/catalog-frontend     172.17.8.103/172.17.8.103   name=catalog,uses=product,navigation,cart,shop   Running
mongodb             10.244.5.2          mongodb             dockerfile/mongodb           172.17.8.102/172.17.8.102   name=mongodb,role=database                       Running
navigation          10.244.102.3        navigation          zutherb/navigation-service   172.17.8.103/172.17.8.103   name=navigation,role=service                     Running
navigation-oh43e    10.244.102.2        navigation          zutherb/navigation-service   172.17.8.103/172.17.8.103   name=navigation,role=backend,uses=mongodb        Running
product             10.244.5.4          product             zutherb/product-service      172.17.8.102/172.17.8.102   name=product,role=service                        Running
product-gziey       10.244.102.4        product             zutherb/product-service      172.17.8.103/172.17.8.103   name=product,role=backend,uses=mongodb           Running
redis               10.244.59.4         redis               dockerfile/redis             172.17.8.104/172.17.8.104   name=redis,role=database                         Running
shop                10.244.59.3         shop                zutherb/monolithic-shop      172.17.8.104/172.17.8.104   name=shop,role=frontend,uses=mongodb,cart        Running

kubectl rollingupdate catalog -f catalog-controller-v2.json --update-period="5s"
Creating catalog-v2
Updating catalog replicas: 2, catalog-v2 replicas: 1
Updating catalog replicas: 1, catalog-v2 replicas: 2
Updating catalog replicas: 0, catalog-v2 replicas: 3
Update succeeded. Deleting catalog
catalog-v2

kubectl rollingupdate catalog-v2 -f catalog-controller.json --update-period="5s"
Creating catalog
Updating catalog-v2 replicas: 2, catalog replicas: 1
Updating catalog-v2 replicas: 1, catalog replicas: 2
Updating catalog-v2 replicas: 0, catalog replicas: 3
Update succeeded. Deleting catalog-v2
catalog
```

[![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/microservice-appserver-youtube.png)](http://www.youtube.com/watch?v=hI2CdIOkqQ4)

## Debugging

```journalctl``` allows you to filter the output by specific fields. Be aware that if there are many messages to display
or filtering of large time span has to be done, the output of this command can be delayed for quite some time.

```
journalctl -f -u docker.service
journalctl -f -u kube-apiserver.service
journalctl -f -u kube-controller-manager.service
journalctl -f -u kube-scheduler.service
journalctl -f -u kube-register.service
```

```
docker ps -a
```

## Logging

[Elk](http://blog.raintown.org/2014/11/logging-kubernetes-pods-using-fluentd.html)
