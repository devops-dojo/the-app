# Overall infrastructure

The automated process with Vagrant/Ansible provisions and configures the CD pipeline and the application.

```cmd
.     "Dojo"                 "DojoLabs"     |
  Resource Group           Resource Group   |
                                            |  Only Azure
+---------------+ Peering +--------------+  |
|   Dojo|VNet   +----------|DojoLabsVNet |  |
+---------------+         +--------------+  |
                     |
                     |
+---------------+    |    +--------------+  |  +-------------+
| DojoInstaller |    |    |elasticsearch |  |  |dbserver     |
|      VM       |    |    |              |  |  |             |
| (only Azure)  |    |    |              |  |  |             |
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

## Installing

There are 3 options to install the 8 VMs which compose the environment:

1. [Locally on Virtualbox](#1-locally-in-virtualbox)
2. [On pre-existing servers or VMs](#2-in-existing-servers-or-vms)
3. [In Azure public or private cloud](#3-on-azure-cloud)

### 1. Locally in Virtualbox

Provided that you have a big enough PC or MAC (16GB memory), you can install
everything locally. This has been successfully tested on Windows 7, Windows 10
and MacOS X laptops/desktops.

* Tools used on your Windows or MAC or Linux physical host:
  * Vagrant
  * Virtualbox
* Tools on guest machines:
  * Ansible (automatically installed by host)

Procedure for local install:

* Install [Virtualbox](https://www.virtualbox.org/wiki/Downloads)
* Install [Vagrant](https://www.vagrantup.com/downloads.html)
* Install Vagrant plugins:

```cmd
vagrant plugin install vagrant-hostsupdater
```

* Clone the repository

```cmd
git clone https://github.com/devops-dojo/the-app.git
cd the-app/vagrant
```

* By default, boxes are meant to be configured behind a web proxy. Tweak with_proxy and/or web proxy host/port
if needed in `provision/vars/default.yml`
* Start provisioning (create VM, use Ansible to install applications)

```cmd
vagrant up --provider=virtualbox --provision
```

* Once everything is done (:coffee:), wait for the [Jenkins jobs](http://ci-node:8080) to complete
* All nodes are accessible following [this table](#nodes)
* Deploy the application as per [this](#deploy-application-on-servers)

### 2. In existing servers or VMs

You can also install the environment on existing virtual machines. You need 8
VMs with **Ubuntu 16.04**. Check the
[`nodes.json`](https://github.com/devops-dojo/the-app/blob/master/vagrant/nodes.json)
file for host names and details on sizing.

* Tools used on host machine: Not applicable
* Tools on guest machines: Ansible

***Note***: all VMs need to run **Ubuntu 16.04** (Xenial) before attempting
to use Ansible.

Procedure to install on existing machines:

* Clone the repository

```cmd
git clone https://github.com/devops-dojo/the-app.git
cd the-app/vagrant/scripts
```

* By default, boxes are meant to be configured behind a web proxy. Tweak with_proxy and/or web proxy host/port
if needed in `../provision/vars/default.yml`

* Start provisioning: for each host, pick one of the line, depending on the host you need to provision:

```cmd
sh ./provision.sh --limit=monitoring-node monitoringserver.yml
sh ./provision.sh --limit=ci-repo reposerver.yml
sh ./provision.sh --limit=mongodb-node databaseserver.yml
sh ./provision.sh --limit=app-server-node-1 monolitic_appserver.yml
sh ./provision.sh --limit=app-server-node-2 monolitic_appserver.yml
sh ./provision.sh --limit=app-server-node-3 micro_appserver.yml
sh ./provision.sh --limit=app-server-node-4 micro_appserver.yml
sh ./provision.sh --limit=ci-node buildserver.yml
```

NOTE: to refresh the install with the latest on github, run `provision.sh` with `--local` argument, like so:

```cmd
sh ./provision.sh --local --limit=ci-node buildserver.yml
```

* Once everything is done (:coffee:), wait for the [Jenkins jobs](http://ci-node:8080) to complete
* All nodes are accessible following [this table](#nodes)
* Deploy the application as per [this](#deploy-application-on-servers)

### 3. On Azure Cloud

The DojoInstaller VM is permanent and is used as a controller for the rest
of the provisioning process.

* Tools used on host machine: Vagrant with [Azure provider](https://github.com/ojacques/vagrant-azure)
* Tools on guest machines: Ansible

#### Azure's Dojo Installer VM

- On Azure, create an **Ubuntu 16.04 VM** (Standard_A1 or Standard_A0), or even smaller
- Create the VM in location "Central US" (centralus).
- :exclamation: If you can't use "centralus" location, make sure to update
[`pre-requisites/params.json`](pre-requisites/params.json), [`pre-requisites/pre-requisites.sh`](pre-requisites/pre-requisites.sh) and [`Vagrantfile`](Vagrantfile) to update the location.
- Make sure that the VirtualNetwork is called "Dojo-vnet" (or update the
  [params.json](pre-requisites/params.json) file)
- Create an `install` directory

```cmd
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
- As we are not behind a web proxy (public cloud), let's tweak the default Ansible config:
```
sed -i -e 's#with_proxy: true#with_proxy: false#' provision/vars/default.yml
```
- Create insecure_private_key.pub in cd /home/YOUR_USER/.vagrant.d
  Copy the content from here: https://github.com/mitchellh/vagrant/blob/master/keys/vagrant.pub

- Install azure cli with npm
```
curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo npm install -g azure-cli
```
- Get your Azure's application details which is used to provision resources:
  - tenant_id: from help/show diagnostics
  - subscription_id: from subscription screen
  - client_id / client_secret: create a new application from "App Registrations" *
    and get a key. client_id = Application ID, client_secret = Application key
  - In Azure Subscription / IAM: add the application you just created and give it
    contributor permissions:
    portal.azure.com > More services > Subscriptions > Visual Studio Enterprise > Access Control (IAM) > Add > Select a Role >  Contributor
    Add users > THE_APP_YOU_CREATED_IN_PREVIOUS_STEP > select > ok

  Alternatively, you can connect once using your regular subscription with the azure login command, then interact with the client to retrieve all IDs and create the "application" as illustrated in [this session log](doc/azure-CLI-create-SP-sample.txt).

- Create Azure cloud credentials .env file with the information from previous step

  You need a `.env`  file which includes your Azure details in `~/the-app/vagrant`.
This file is sourced by several install scripts.

```cmd
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

### Provision DojoLabs

#### Setting up DojoLabs resource group

`pre-requisites.sh` will:
- Create DojoLabs resource Group
- add a virtual network
- Peer that virtualnetwork with the one Dojo-vnet  virtual network in the
Dojo resource group.

```
cd pre-requisites
azure login
pre-requisites.sh
cd ..
```

#### Provision application and Pipeline

Launch the script which starts Vagrant to provision the VMs, and Ansible
to configure them.
```
./azure_start.sh
```

:clock2: The process will take between 1 and 2 hours (serial). Plan in advance.

In case a step fails in the Ansible playbook, you can ssh to the box and run it
again using this command:

```
ansible-playbook -c local --extra-vars=ansible_ssh_user='vagrant' --inventory-file=/provision/hosts \
   -v --private-key=~/.vagrant.d/insecure_private_key \
   --limit=ci-node /provision/buildserver.yml
```

If you want to delete a machine and create it again from scratch, run:
```
LAB_SUFFIX=team1 vagrant up --provider=azure --no-parallel buildserver
```

# Deploy application on servers

You have to run the [Production Deployment Builds](http://ci.microservice.io:8080/view/Production%20Deployment/) on the
[Jenkins CI Server](http://jenkins-ci.org/) after you have boot up the cluster. Otherwise you can not use the production urls
that are given in the next section. Therefore you have to run the following two builds:

- [Microservice Production Deployment](http://ci.microservice.io:8080/view/Production%20Deployment/job/shop-microservice-production-deployment/build?delay=0sec)
- [Monolith Production Deployment](http://ci.microservice.io:8080/view/Production%20Deployment/job/shop-monolitic-production-deployment/build?delay=0sec)

Please check if all builds are green sometimes the catalog ui build fails and must be re run until it is green.

![CI-Node](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/production-deployment.png)

After you have complete this, the cluster is fully installed and you can start to work with it.

# Nodes

Here is a list of nodes with links for the services.

Note that 10.x.x.x IP addresses cannot be reached except from the provisioning system or from another host of the cluser.
All hosts are aliased using `/etc/hosts` - hence links like microservice.io and monolith.io.

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
appserver4    | 10.211.55.104 | app-server-node-4  | Microservice Shop PRO (LB)  | http://shop.microservice.io/
appserver5    | 10.211.55.105 | app-server-node-5  | Microservice Shop PRO       | http://shop.microservice.io/
elasticsearch | 10.211.55.100 | monitoring-node    | Kibana                      | http://monitoring.microservice.io/
elasticsearch | 10.211.55.100 | monitoring-node    | Nagios                      | http://monitoring.microservice.io/nagios3/ (nagiosadmin / admin123)
elasticsearch | 10.211.55.100 | monitoring-node    | Icinga                      | http://monitoring.microservice.io/icinga/ (icingaadmin / admin123)
