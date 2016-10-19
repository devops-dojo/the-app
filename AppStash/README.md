# AppStash - Microservice Phone Shop Application

## Overview

This application gives software architects and developers an example how a microservice web application can look like
and it simulates a development cluster, which contains continuous integration infrastructure as well as all
necessary nodes that are needed to run an online shop. Thus it will furthermore shown, how a distributed online shop can
deployed with a multi deployment pipeline and how the distributed system can be monitored. The application is based on
the following two online shop applications, which can be found on Github:
- [AngularJS Phone Catalog](https://github.com/angular/angular-phonecat)
- [MongoDB Pizza Shop](https://github.com/comsysto/mongodb-onlineshop)

Both project was combined to an new online shop that is indeed to sell mobile devices and implements the following use
cases. An user is able to:
- see different kinds of mobile devices catalogs (e.g. mobiles or tablets),
- create a cart,
- and order the created cart.

![Use Case Online Shop](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/use_case_online_shop.png)

This use cases are implemented in the following two ways:

- A [Monolitic Webshop](https://github.com/zutherb/AppStash/#monolith-appserver), which is represented by a three layered
  online shop based on [Apache Wicket](http://wicket.apache.org/), the [Spring Framework](http://projects.spring.io/spring-framework/)
  and [Spring Data](http://projects.spring.io/spring-data/) that implements all given use cases,
- and microservice architecture, which is based on a mix of the Monolitic Webshop and a [Microservice Catalog Frontend](https://github.com/zutherb/AppStash/#microservice-appserver)
  as it is shown in the below deployment diagram. In this mix a so called Microservice Catalog Frontend provides the
  use case that an user should be able to see the different mobile. Finally the Monolitic Webshop is used by the user
  to create an order that means Monolitic Webshop represents a microservice on its own for this specific use case.
  The Microservice Catalog Frontend is based on an [AngularJS](https://angularjs.org/) and [Typescript](http://www.typescriptlang.org/)
  which access different kinds of [REST-Services](http://en.wikipedia.org/wiki/Representational_state_transfer) that are
  implemented in [Scala](http://www.scala-lang.org/), [Spray](http://spray.io/), [Restx](http://restx.io/) and [Spring Boot](http://projects.spring.io/spring-boot/).

![Deployment Diagram Online Shop](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/deployment_diagramm_online_shop.png)

## Used technologies

For simplicity all services are supposed to run on a Java-VM at the moment.

* Web frontend
  * Based on Angular JS and Typescript
* Cart service
  * Based on Spring boot and Groovy
  * Redis backend via Spring Data
* Product backend
  * Based on Scala and Spray
  * MongoDB backend via ReactiveMongo
* Navigation backend
  * Based on Restx with embedded Jetty
  * MongoDB backend via Jongo

## Presentations

Date       | Event                         | Title
-----------|-------------------------------|----------------------------------------------------------------------------------------
12.01.2015 | [JUGM](http://www.jugm.de/)   | [Next Generation IT - Qual oder Segen für den Entwickler](http://zutherb.github.io/AppStash/slides/01_jugm/)
23.01.2015 | [bobkonf](http://bobkonf.de/) [Video (german)](https://www.youtube.com/watch?v=G7CmsYNKP4A) | [Microservices und die Jagd nach mehr Konversion - Fluch oder Segen für den Entwickler](http://zutherb.github.io/AppStash/slides/02_bobkonf/)
26.01.2015 | [Microservices Meetup Munich](http://www.meetup.com/Microservices-Meetup-Munich/) [Video (german)](http://youtu.be/t6YfKMFvPvs)| [Microservices und die Jagd nach mehr Konversion](http://zutherb.github.io/AppStash/slides/03_microservice_usergroup_munich/)
24.02.2015 | [Microservices Meetup Berlin](http://www.meetup.com/Microservices-Meetup-Berlin/) | [Microservices & Conversion Hunting - Software architectures for changeableness](http://zutherb.github.io/AppStash/slides/04_microservice_usergroup_berlin/)
09.03.2015 | [Microservices Meetup Hamburg](http://www.meetup.com/Microservices-Meetup-Hamburg/) | [Microservices und die Jagd nach mehr Konversion](http://zutherb.github.io/AppStash/slides/05_microservice_usergroup_hamburg/)
25.03.2015 | [Javaland](http://javaland.eu/) | [Die Jagd nach mehr Konversion - Fluch oder Segen für den Entwickler](http://zutherb.github.io/AppStash/slides/06_javaland/)
15.04.2015 | [confess](https://www.regonline.com/builder/site/Default.aspx?EventID=1619724) | [Microservices and Conversion Hunting - How to build software architectures for changeableness](http://zutherb.github.io/AppStash/slides/07_confess/)
21.04.2015 | [Agile Softwarearchitektur Münster](http://www.meetup.com/Agile-Softwarearchitektur/) | [Microservices und die Jagd nach mehr Konversion](http://zutherb.github.io/AppStash/slides/08_agile_softwarearchitektur_muenster/)
23.06.2015 | [Devoxx Poland](http://devoxx.pl/) | [Microservices and Conversion Hunting - How to build software architectures for adaptability](http://zutherb.github.io/AppStash/slides/09_devoxx_pl/)
20.08.2015 | [IT Meetup Bali](http://www.meetup.com/IT-Meetup-Bali/events/224324564) | [Microservices and Conversion Hunting: Build Architectures for Changeability](http://zutherb.github.io/AppStash/slides/10_bali/)
14.09.2015 | [Microservices Meetup Hamburg](http://www.meetup.com/Microservices-Meetup-Hamburg/) | [Microservice-Deployment ganz einfach](http://zutherb.github.io/AppStash/slides/11_ljugm/)
15.09.2015 | [Lightweight Java User Group München](http://www.meetup.com/de/lightweight-java-user-group-munchen/) | [Microservice-Deployment ganz einfach](http://zutherb.github.io/AppStash/slides/11_ljugm/)
30.09.2015 | [code.talks](http://www.codetalks.de/) | [Microservices und die Jagd nach mehr Konversion](http://zutherb.github.io/AppStash/slides/12_codetalks)
06.10.2015 | [Java Forum Nord](http://www.java-forum-nord.de/) | [Microservices und die Jagd nach mehr Konversion](http://zutherb.github.io/AppStash/slides/13_java_forum_nord)
29.10.2015 | [JavaOne](https://www.oracle.com/javaone/index.html) | [Microservices and Conversion Hunting: Build Architectures for Changeability](http://zutherb.github.io/AppStash/slides/14_javaone)
03.11.2015 | [W-Jax](https://jax.de/wjax2015/) | [Von Null auf Hundert mit Microservices](http://zutherb.github.io/AppStash/slides/15_wjax)
28.01.2016 | [DevOps Karlsruhe Meetup](http://www.meetup.com/de-DE/DevOps-Karlsruhe-Meetup/) | [Von Null auf Hundert mit Microservices](http://zutherb.github.io/AppStash/slides/16_karlsruhe)

## Articles

Title | Language
------|-----------------
[Microservices und die Jagd nach mehr Konversion – das Heilmittel für erkrankte IT-Architekturen?](http://bernd-zuther.de/wp-content/uploads/2015/02/02-2015-Java-aktuell-Bernd-Zut-her-Microservices-und-die-Jagd-nach-mehr-Konversion-das-Heilmittel-fu%CC%88r-erkrankte-IT-Architekturen.pdf) | German
[Microservice-Deployment ganz einfach mit Giant Swarm](https://blog.codecentric.de/2015/05/microservice-deployment-ganz-einfach-mit-giant-swarm/) | German
[Microservice-Deployment ganz einfach mit Kubernetes](https://blog.codecentric.de/2015/05/microservice-deployment-ganz-einfach-mit-kubernetes/) | German
[Microservice-Deployment ganz einfach mit Docker Compose](https://blog.codecentric.de/2015/05/microservice-deployment-ganz-einfach-mit-docker-compose/) | German
[Microservice-Deployment ganz einfach ohne Docker mit der Linux-Paketverwaltung](https://blog.codecentric.de/2015/05/microservice-deployment-ganz-einfach-ohne-docker-mit-der-linux-paketverwaltung/) | German
[Canary-Releases mit der Very Awesome Microservices Platform (Vamp)](https://blog.codecentric.de/2015/10/canary-release-mit-der-awesome-microservices-platform-vamp/) | German
[In 10 Minuten zum Kubernetes Cluster auf AWS](https://blog.codecentric.de/2015/12/in-10-minuten-zum-kubernetes-cluster-auf-aws/) | German

## Directory Layout

The following directory layout shows only the important directories that are necessary to implement the given use cases
in the [overview](https://github.com/zutherb/AppStash/#overview).

    microservice/           --> all files of the microservice applications are located in this folder
        frontend/           --> all microservice frontend applications are located in this folder
            catalog/        --> an AngularJS frontend application that shows the product catalog and is used to create a cart is located in this directory
            checkout/       --> all files that are needed to glue the checkout form of the monolithic to the microservice catalog frontend
        service/            --> all business services are located in the folder
            cart/           --> a spring boot cart rest service is located in the folder
            navigation/     --> a java based restx navigation rest service is located in the folder
            product/        --> a scala spray product rest service is located in the folder
    monolithic/             --> all files of the monolithic application are located in this directory

## Prerequisites

You need some dependencies to run the application cluster or to add add own services to the showcase application.

###Running 

You need at least 16 GB RAM to run the whole cluster that emulates a whole development environment like you can find it
in the must professional software development projects. Furthermore you have to install the following software
dependencies on your machine. If you want to run the shop applications with lower memory you has to uses [Docker](https://github.com/zutherb/AppStash/blob/master/compose/README.md)
or [Kubernetes](https://github.com/zutherb/AppStash/blob/master/kubernetes/README.md).

The provisioning should work on other systems as well but is only tested on MacOSX 10.10. That is the reason because the
whole instructions that are shown, are related to MacOSX.

#### Git

- Git [home](http://git-scm.com/) (download, documentation) is a distributed revision control system.
- A good place to learn about setting up git is [here](https://help.github.com/articles/set-up-git)
- You should install Git with [Homebrew](http://brew.sh/)

```
brew install git
```

#### Vagrant

- Vagrant creates and configures lightweight, reproducible, and portable development environments. You do not have to
learn much about Vagrant, but you should be able to install it and execute the following commandline: ```vagrant up```
- [Vagrant](https://www.vagrantup.com/) (download, documentation)
- You should install Vagrant with [Homebrew](http://brew.sh/)

```
brew tap caskroom/cask
brew install brew-cask
brew cask install virtualbox
brew cask install vagrant
brew cask install vagrant-manager
```

#### Ansible

- [Ansible](http://www.ansible.com/) (download, documentation) is a tool for automating infrastructure orchestration.
  You must not know Ansible, but you have to install Ansible otherwise Vagrant is not able to create the virtual machines.
- You should install Ansible with [Homebrew](http://brew.sh/)

```
brew install ansible
```

###Boot up the cluster

The only thing you have to do to run the whole microservice cluster is to execute the following commands:

```bash
git clone git@github.com:zutherb/AppStash.git appstash
cd appstash/vagrant
vagrant plugin install vagrant-cachier
vagrant plugin install vagrant-hostsupdater
vagrant up
```

Vagrant will provision each node in the cluster with all the necessary components (e.g. Monitoring, Build, Database,
Debian repository and Application Server). The initial setup can take a few minutes.

### Deploy on production servers

You have to execute the [Production Deployment Builds](http://ci.microservice.io:8080/view/Production%20Deployment/) on the
[Jenkins CI Server](http://jenkins-ci.org/) after you have boot up the cluster. Otherwise you can not use the production urls
that are given in the next section. Therefore you have to execute the following two builds:

- [Microservice Production Deployment](http://ci.microservice.io:8080/view/Production%20Deployment/job/shop-microservice-production-deployment/build?delay=0sec)
- [Monolith Production Deployment](http://ci.microservice.io:8080/view/Production%20Deployment/job/shop-monolitic-production-deployment/build?delay=0sec)

Please check if all builds are green sometimes the catalog ui build fails and must be re run untill it is green. 

![CI-Node](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/production-deployment.png)

After you have complete this, the cluster is fully installed and you can start to work with it.

## Workings with the application cluster

The Cluster contains of the following nodes:

Vargrant-Name | IP            | Hostname           | Application                 | Forward
--------------|---------------|--------------------|-----------------------------|--------------------------------------------------------------------
buildserver   | 10.211.55.200 | ci-node            | Jenkins                     | http://ci.microservice.io:8080/
reposerver    | 10.211.55.201 | ci-repo            | Artifact Repository (NGINX) |
dbserver      | 10.211.55.202 | mongodb-node       | MongoDB                     | localhost:27017
dbserver      | 10.211.55.202 | redis-node         | Redis                       | localhost:6379
appserver1    | 10.211.55.101 | app-server-node-1  | Legacy Shop                 | http://test.monolith.io:8080/shop/
appserver1    | 10.211.55.101 | app-server-node-1  | Probe                       | http://test.monolith.io:8080/probe/ (admin / topsecret)
appserver2    | 10.211.55.102 | app-server-node-2  | Legacy Shop                 | http://shop.monolith.io:8080/shop/
appserver2    | 10.211.55.102 | app-server-node-2  | Probe                       | http://shop.monolith.io:8080/probe/ (admin / topsecret)
appserver3    | 10.211.55.103 | app-server-node-3  | Microservice Shop           | http://test-shop.microservice.io/
appserver3    | 10.211.55.104 | app-server-node-4  | Microservice Shop           | http://shop.microservice.io/
elasticsearch | 10.211.55.100 | monitoring-node    | Kibana                      | http://monitoring.microservice.io/
elasticsearch | 10.211.55.100 | monitoring-node    | Nagios                      | http://monitoring.microservice.io/nagios3/ (nagiosadmin / admin123)
elasticsearch | 10.211.55.100 | monitoring-node    | Icinga                      | http://monitoring.microservice.io/icinga/ (icingaadmin / admin123)

###CI-Node

A Jenkins build server is running on the CI-Node. Jenkins is an open source continuous integration tool written in Java
that provides a continuous integration services for software development which supports diffent SCM tools. Furthermore
Jenkins can execute different build scripts like [Gradle](http://gradle.org/) as well as arbitrary
shell scripts and Windows batch commands.

You can reach the jenkins that builds and deploy the monolith and microservice application under the following url http://ci.microservice.io:8080/.

![CI-Node](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/ci-node.png)

###Monolith Appserver

The monolith online shop is deployed on the Monolith Appserver which is a reference implementation for the given use
cases in the [Overview](https://github.com/zutherb/AppStash/#overview). You can reach the online shop under the following
url http://shop.monolith.io:8080/shop/ .

![Monolith Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/monolith-appserver.png)

Furthermore you can reach the [PSI Probe](https://code.google.com/p/psi-probe/) monitoring and log analysis services
under the following url http://shop.monolith.io:8080/probe/. The user credentials are admin / topsecret.

PSI Probe is a community-driven fork of Lambda Probe, which is intended to replace the Tomcat Manager and should make
it easier to manage and monitor an instance of Apache Tomcat. PSI Probe does not require any changes to an existing app
and it provides many features through a web-accessible interface that becomes available simply by deploying it to your
server. These features include:

- Requests: Monitor traffic in real-time, even on a per-application basis.
- Sessions: Browse/search attributes, view last IP, expire, estimate size.
- Logs: View contents, download, change levels at runtime.
- Threads: View execution stack, kill.
- JVM: Memory usage charts, advise GC.

![Probe](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/probe.png)
![Probe](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/probe-log.png)

Moreover you access the performance monitor [JETM](http://jetm.void.fm/) under the following url http://shop.monolith.io:8080/shop/performance/
which is a small and free library that is included in the monolith online shop, that helps locating performance problems
in existing Java applications. JETM enables developers to track down performance issues on demand, either programmatic
or declarative with minimal impact on application performance, even in production.

![JETM Overview](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/performance-overview.png)
![JETM Request view](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/performance-request-view.png)

[JMX](http://en.wikipedia.org/wiki/Java_Management_Extensions) is a natural way to have access to technical management,
e.g. for tuning, statistics, log levels and so on. Unfortunately, it lacks a lightweight tool to expose mbeans and to browse
them securely on any application and environment without heavy infrastructure setup. [JMiniX](https://code.google.com/p/jminix/)
provides such a feature. You can reach JMiniX under the following url http://shop.monolith.io:8080/shop/jmx/.

![JMiniX](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/jminix.png)

###Microservice Appserver

The microservice based online shop is deployed on the microservice appserver which is a reference implementation for the
given use cases in the [Overview](https://github.com/zutherb/AppStash/#overview). You can reach the online shop under the
following url http://shop.microservice.io/ .

![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/microservice-appserver.png)

The microservice based online shop consists of two frontend parts as you can see in the deployment diagram in the
[overview section](https://github.com/zutherb/AppStash/#overview). The first part is an AngularJS Catalog Frontend that
makes it possible to see a catalog for mobiles as well as a catalog for tablets. Furthermore a user is able to create a
cart. If a user wants to order a created cart there is same clue logic in the [monolithic web application](https://github.com/zutherb/AppStash/#monolith-appserver)
that a cart which was created in the AngularJS Catalog Frontend can be order with the checkout of the Wicket online shop
on the monolith appserver.

###Monitoring Server

Monitoring a monolithic web application is no major pain as you can see in the [monolith appserver section](https://github.com/zutherb/AppStash/#monolith-appserver).
A distributed web application, like it is shown in the [microservice appserver section](https://github.com/zutherb/AppStash/#microservice-appserver),
is not so easy to monitor. In this small example there is a [Ngnix](http://nginx.org/) web server that logs all request
that comes into it. The Ngnix deliveries a AngularJS Catalog Frontend that represents a A single-page application (SPA).
A SPA is a web application that fits on a single web page with the goal of providing a more fluid user experience akin to
a desktop application. In addition to the SPA there are the three rest services a cart, product and a navigation service
which are need for the different uses cases and are implemented in the programming languages Groovy, Scala and Java.
This services must be alive that an user can see the products or create a cart. Furthermore there is a legacy JEE web
application which is deployed in a [Tomcat Webserver](http://tomcat.apache.org/). An user can order its cart with that
legacy JEE web application.

Icinga is an open source network and computer system monitoring application. It was originally created as a fork of
the Nagios system monitoring application. Icinga is attempting to get past perceived short-comings in Nagios development
process, as well as adding new features such as a modern Web 2.0 style user interface, additional database connectors,
and a REST API that lets administrators integrate numerous extensions without complicated modification of the Icinga core.

![Icinga Status Map](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/icinga-status-map.png)
![Icinga Status Report](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/icinga-status.png)

Kibana is a browser based analytics and search interface for Elasticsearch that was developed primarily to view
Logstash event data. Logstash is a tool that can be used to collect, process and forward events and log messages.
Collection is accomplished via number of configurable input plugins including raw socket/packet communication,
file tailing and several message bus clients. Once an input plugin has collected data it can be processed by any number
of filters which modify and annotate the event data. Finally events are routed to output plugins which can forward the
events to a variety of external programs including Elasticsearch, local files and several message bus implementations.

![Kibana](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/kibana.png)

## Contact

If you have any questions or remarks, please don't hesitate to contact me. For feature requests or general feedback,
you can also use the [issue tracker](https://github.com/zutherb/AppStash/issues)

[Bernd Zuther](mailto:bernd.zuther@me.com)

## Licensing

This work is open source, and is licensed under the Apache License, Version 2.0.
