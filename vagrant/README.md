# Pre-requisites

## Overall infrastructure

The automated process with Vagrant/Ansible provisions and configures the CD pipeline and the application.


```
.     "Dojo"                 "DojoLabs"
  Resource Group           Resource Group

+---------------+ Peering +--------------+
|   Dojo|VNet   +----------|DojoLabsVNet |
+---------------+         +--------------+
                     |
                     |                      +
+---------------+    |    +--------------+  |  +-------------+
| DojoInstaller |    |    |elasticsearch |  |  |dbserver     |
|      VM       |    |    |              |  |  |             |
|               |    |    |              |  |  |             |
+---------------+    |    +--------------+  |  +-------------+
                     |                      |
                     |    +--------------+  |  +-------------+   +--------------+
                     |    |reposerver    |  |  |appservr1 TST|   |appservr3 TST |
                     |    |              |  |  |(monolithic) |   |(microservice)|
                     |    |              |  |  |             |   |              |
                     |    +--------------+  |  +-------------+   +--------------+
                     |                      |
                     |    +--------------+  |  +-------------+   +--------------+
                     |    |buildserver   |  |  |appservr2 PRO|   |appservr4 PRO |
                     |    |              |  |  |(monolithic) |   |(microservice)|
                     |    |              |  |  |             |   |              |
                     |    +--------------+  |  +-------------+   +--------------+
                     |                      |
+--------------------+----------------------+---------------------------------------
    Provisioning            CD Pipeline                  Application

```

The DojoInstaller VM is permanent and is used as a controller for the rest
of the provisioning process.
Although designed originally with Vagrant/Virtualbox, the process is now leveraging
Microsoft Azure public cloud.

## Installing


### DojoInstaller VM

- On Azure, create an Ubuntu 16.10 VM (Standard_A1 or Standard_A0).
- Create the VM in location "Central US" (centralus).
- :exclamation: If you can't use "centralus" location, make sure to update
[`pre-requisites/params.json`](pre-requisites/params.json), [`pre-requisites/pre-requisites.sh`](pre-requisites/pre-requisites.sh) and [`Vagrantfile`](Vagrantfile) to update the location.
- Make sure that the VirtualNetwork is called "Dojo-vnet" (or update the
  [params.json](pre-requisites/params.json) file)
- Create an `install` directory
```
mkdir install
cd install
```
- Install Vagrant
```
sudo apt-get update
sudo apt-get install moreutils
wget https://releases.hashicorp.com/vagrant/1.8.6/vagrant_1.8.6_x86_64.deb
sudo dpkg -i vagrant_1.8.6_x86_64.deb
vagrant box add azure https://github.com/azure/vagrant-azure/raw/v2.0/dummy.box
```
- Install Vagrant plugins:
```
vagrant plugin install vagrant-hostsupdater
vagrant plugin install vagrant-proxyconf
```
- Install the custom Azure provider for Vagrant:
```
git clone https://github.com/ojacques/vagrant-azure.git
cd vagrant-azure
sudo apt-get install ruby
gem build vagrant-azure.gemspec
vagrant plugin install ./vagrant-azure-2.0.0.pre1.dojo.gem
cd ..
```
- Install Ansible >=2.1
```
sudo apt-get install software-properties-common
sudo apt-add-repository ppa:ansible/ansible
sudo apt-get update
sudo apt-get install ansible
```
- Clone the repository
```
git clone https://github.com/devops-dojo/the-app.git
cd the-app/vagrant
```
- Create Azure cloud credentials .env file

  You need a `.env`  file which includes your Azure details in `~/the-app/vagrant`.
This file is sourced by several install scripts.

```
# Include your Azure details
export AZURE_SUBSCRIPTION_ID="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
export AZURE_TENANT_ID="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
export AZURE_CLIENT_ID="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
export AZURE_CLIENT_SECRET="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"

export VAGRANT_LOG=error
```

:metal::smile::metal:
You're done installing the DojoInstaller VM. Let's provision our Dojo
lab using vagrant!

## Provision DojoLabs

### Setting up DojoLabs resource group

`pre-requisites.sh` will:
- Create DojoLabs resource Group
- add a virtual network
- Peer that virtualnetwork with the one Dojo-vnet  virtual network in the
Dojo resource group.

```
cd pre-requisites
pre-requisites.sh
cd ..
```

### Provision application and Pipeline

Launch the script which starts Vagrant to provision the VMs, and Ansible
to configure them.
```
./azure_start.sh
```

:clock2: The process will take between 1 and 2 hours (serial). Plan in advance.

In case a step fails in the Ansible playbook, you can run it again using this command:

```
/usr/bin/ansible-playbook --connection=ssh --timeout=30 \
   --extra-vars=ansible_ssh_user='vagrant' --inventory-file=provision/hosts \
   -v --private-key=~/.vagrant.d/insecure_private_key \
   --limit=ci-node provision/buildserver.yml
```

### Deploy application on servers

You have to run the [Production Deployment Builds](http://ci.microservice.io:8080/view/Production%20Deployment/) on the
[Jenkins CI Server](http://jenkins-ci.org/) after you have boot up the cluster. Otherwise you can not use the production urls
that are given in the next section. Therefore you have to run the following two builds:

- [Microservice Production Deployment](http://ci.microservice.io:8080/view/Production%20Deployment/job/shop-microservice-production-deployment/build?delay=0sec)
- [Monolith Production Deployment](http://ci.microservice.io:8080/view/Production%20Deployment/job/shop-monolitic-production-deployment/build?delay=0sec)

Please check if all builds are green sometimes the catalog ui build fails and must be re run until it is green.

![CI-Node](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/production-deployment.png)

After you have complete this, the cluster is fully installed and you can start to work with it.

### Nodes

Here is a list of nodes, with a link when you can access them from the public
internet.

Note that 10.x.x.x IP addresses cannot be reached, except from the provisioning VM (thanks to virtual network peering) or any other VM in DojoLabsVNet.

To `ssh` to a box from the provisioning VM, just type `ssh vagrant@10.211.55.200`
or `vagrant ssh buildserver`.


Vagrant-Name  | IP            | Hostname           | Application                 | Forward
--------------|---------------|--------------------|-----------------------------|--------------------------------------------------------------------
buildserver   | 10.211.55.200 | ci-node            | Jenkins                     | http://ci.microservice.io:8080/
reposerver    | 10.211.55.201 | ci-repo            | Artifact Repository (NGINX) | http://ci-repo.microservice.io
dbserver      | 10.211.55.202 | mongodb-node       | MongoDB                     | mongo.microservice.io:27017
dbserver      | 10.211.55.202 | redis-node         | Redis                       | redis.microservice.io:6379
appserver1    | 10.211.55.101 | app-server-node-1  | Legacy Shop TST             | http://test.monolith.io:8080/shop/
appserver1    | 10.211.55.101 | app-server-node-1  | Probe TST                   | http://test.monolith.io:8080/probe/ (admin / topsecret)
appserver2    | 10.211.55.102 | app-server-node-2  | Legacy Shop PRO             | http://shop.monolith.io:8080/shop/
appserver2    | 10.211.55.102 | app-server-node-2  | Probe PRO                   | http://shop.monolith.io:8080/probe/ (admin / topsecret)
appserver2    | 10.211.55.102 | app-server-node-2  | JETM Performance monitor    | http://shop.monolith.io:8080/shop/performance/
appserver2    | 10.211.55.102 | app-server-node-2  | JMX info                    | http://shop.monolith.io:8080/shop/jmx/
appserver3    | 10.211.55.103 | app-server-node-3  | Microservice Shop TST       | http://test-shop.microservice.io/
appserver4    | 10.211.55.104 | app-server-node-4  | Microservice Shop PRO       | http://shop.microservice.io/
elasticsearch | 10.211.55.100 | monitoring-node    | Kibana                      | http://monitoring.microservice.io/
elasticsearch | 10.211.55.100 | monitoring-node    | Nagios                      | http://monitoring.microservice.io/nagios3/ (nagiosadmin / admin123)
elasticsearch | 10.211.55.100 | monitoring-node    | Icinga                      | http://monitoring.microservice.io/icinga/ (icingaadmin / admin123)
