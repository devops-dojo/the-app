# Microservice Phone Shop Application and CD pipeline

## Introduction
This application and its continuous delivery pipeline is used as training
material during the DevOps Dojo green belt training.

## Overview
This application gives software architects and developers an example how a
microservice web application can look like and it simulates a development
cluster, which contains continuous integration infrastructure as well as all
necessary nodes that are needed to run an online shop. Thus it will furthermore
shown, how a distributed online shop can deployed with a multi deployment
pipeline and how the distributed system can be monitored. The application is
based on the following two online shop applications, which can be found on
Github:
- [AngularJS Phone Catalog](https://github.com/angular/angular-phonecat)
- [MongoDB Pizza Shop](https://github.com/comsysto/mongodb-onlineshop)

Both project were combined to an new online shop that is indeed to sell mobile devices and implements the following use
cases. A user is able to:
- see different kinds of mobile devices catalogs (e.g. mobiles or tablets),
- create a cart,
- and order the created cart.

![Use Case Online Shop](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/use_case_online_shop.png)

This use cases are implemented in the following two ways:

- A [Monolitic Webshop](https://github.com/devops-dojo/the-app/#monolith-appserver), which is represented by a three layered
  online shop based on [Apache Wicket](http://wicket.apache.org/), the [Spring Framework](http://projects.spring.io/spring-framework/)
  and [Spring Data](http://projects.spring.io/spring-data/) that implements all given use cases,
- and microservice architecture, which is based on a mix of the Monolitic Webshop and a [Microservice Catalog Frontend](https://github.com/devops-dojo/the-app/#microservice-appserver)
  as it is shown in the below deployment diagram. In this mix a so called Microservice Catalog Frontend provides the
  use case that an user should be able to see the different mobile. Finally the Monolitic Webshop is used by the user
  to create an order that means Monolitic Webshop represents a microservice on its own for this specific use case.
  The Microservice Catalog Frontend is based on an [AngularJS](https://angularjs.org/) and [Typescript](http://www.typescriptlang.org/)
  which access different kinds of [REST-Services](http://en.wikipedia.org/wiki/Representational_state_transfer) that are
  implemented in [Scala](http://www.scala-lang.org/), [Spray](http://spray.io/), [Restx](http://restx.io/) and [Spring Boot](http://projects.spring.io/spring-boot/).

![Deployment Diagram Online Shop](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/deployment_diagramm_online_shop.png)

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

## Directory Layout

The following directory layout shows only the important directories that are necessary to implement the given use cases
in the [overview](https://github.com/devops-dojo/the-app/#overview).

    microservice/           --> all files of the microservice applications are located in this folder
        frontend/           --> all microservice frontend applications are located in this folder
            catalog/        --> an AngularJS frontend application that shows the product catalog and is used to create a cart is located in this directory
            checkout/       --> all files that are needed to glue the checkout form of the monolithic to the microservice catalog frontend
        service/            --> all business services are located in the folder
            cart/           --> a spring boot cart rest service is located in the folder
            navigation/     --> a java based restx navigation rest service is located in the folder
            product/        --> a scala spray product rest service is located in the folder
    monolithic/             --> all files of the monolithic application are located in this directory
    vagrant/                --> Install the application and the continuous delivery pipeline (local VirtualBox or Microsoft Azure)


## Prerequisites

You need some dependencies to run the application cluster or to add your own services to the showcase application.

###Running

See [vagrant directory](/vagrant) on installing the cluster of machines to run the application and the continuous delivery pipeline. You need at least 16 GB RAM to run the whole cluster in one machine (local virtualbox) or leverage the Azure cloud installer to have different VMs for each function.

If you just want to run the application, use Docker: [Docker](/compose/README.md)


## The application

###Microservice Appserver

The microservice based online shop is deployed on the microservice appserver
which is a reference implementation for the given use cases in the
[Overview](https://github.com/devops-dojo/the-app/#overview). You can reach the
online shop under the following url
http://shop.microservice.io/.

![Microservice Appserver](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/microservice-appserver.png)

The microservice based online shop consists of two frontend parts as you can see
in the deployment diagram in the [overview
section](https://github.com/devops-dojo/the-app/#overview). The first part is an
AngularJS Catalog Frontend that makes it possible to see a catalog for mobiles
as well as a catalog for tablets. Furthermore a user is able to create a cart.
If a user wants to order a created cart there is same clue logic in the
[monolithic web
application](https://github.com/devops-dojo/the-app/#monolith-appserver) that a
cart which was created in the AngularJS Catalog Frontend can be order with the
checkout of the Wicket online shop on the monolith appserver.


###Monolith Appserver

The monolith online shop is deployed on the Monolith Appserver which is a
reference implementation for the given use cases in the
[Overview](https://github.com/devops-dojo/the-app/#overview). You can reach the
online shop under the following url
http://shop.monolith.io:8080/shop/ .

![Monolith Appserver](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/monolith-appserver.png)


## The Continuous Delivery pipeline

### CI-Node

A Jenkins build server is running on the CI-Node. Jenkins is an open source continuous integration tool written in Java
that provides a continuous integration services for software development which supports different SCM tools. Furthermore
Jenkins can execute different build scripts like [Gradle](http://gradle.org/) as well as arbitrary
shell scripts and Windows batch commands.

You can reach the jenkins that builds and deploy the monolith and microservice application under the following url http://ci.microservice.io:8080/.

![CI-Node](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/ci-node.png)


### Monitoring Server

Monitoring a monolithic web application is no major pain as you can see in the [monolith appserver section](https://github.com/devops-dojo/the-app/#monolith-appserver).
A distributed web application, like it is shown in the [microservice appserver section](https://github.com/devops-dojo/the-app/#microservice-appserver),
is not so easy to monitor. In this small example there is a [Ngnix](http://nginx.org/) web server that logs all request
that comes into it. The Ngnix deliveries a AngularJS Catalog Frontend that
represents a A single-page application (SPA). A SPA is a web application that
fits on a single web page with the goal of providing a more fluid user
experience akin to a desktop application. In addition to the SPA there are the
three rest services a cart, product and a navigation service which are need for
the different uses cases and are implemented in the programming languages
Groovy, Scala and Java. This services must be alive that an user can see the
products or create a cart. Furthermore there is a legacy JEE web application
which is deployed in a [Tomcat Webserver](http://tomcat.apache.org/). An user
can order its cart with that legacy JEE web application.

#### Icinga
Icinga is an open source network and computer system monitoring application. It
was originally created as a fork of the Nagios system monitoring application.
Icinga is attempting to get past perceived short-comings in Nagios development
process, as well as adding new features such as a modern Web 2.0 style user
interface, additional database connectors, and a REST API that lets
administrators integrate numerous extensions without complicated modification of
the Icinga core.

![Icinga Status Map](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/icinga-status-map.png)
![Icinga Status Report](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/icinga-status.png)

#### Kibana
Kibana is a browser based analytics and search interface for Elasticsearch that was developed primarily to view
Logstash event data. Logstash is a tool that can be used to collect, process and forward events and log messages.
Collection is accomplished via number of configurable input plugins including raw socket/packet communication,
file tailing and several message bus clients. Once an input plugin has collected data it can be processed by any number
of filters which modify and annotate the event data. Finally events are routed to output plugins which can forward the
events to a variety of external programs including Elasticsearch, local files and several message bus implementations.

![Kibana](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/kibana.png)

#### PSI Probe
Furthermore you can reach the [PSI Probe](https://code.google.com/p/psi-probe/)
monitoring and log analysis services under the following url
http://shop.monolith.io:8080/probe/. The user
credentials are admin / topsecret.

PSI Probe is a community-driven fork of Lambda Probe, which is intended to replace the Tomcat Manager and should make
it easier to manage and monitor an instance of Apache Tomcat. PSI Probe does not require any changes to an existing app
and it provides many features through a web-accessible interface that becomes available simply by deploying it to your
server. These features include:

- Requests: Monitor traffic in real-time, even on a per-application basis.
- Sessions: Browse/search attributes, view last IP, expire, estimate size.
- Logs: View contents, download, change levels at runtime.
- Threads: View execution stack, kill.
- JVM: Memory usage charts, advise GC.

![Probe](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/probe.png)
![Probe](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/probe-log.png)

#### JETM
JETM performance monitor [JETM](http://jetm.void.fm/) is at the following url
http://shop.monolith.io:8080/shop/performance/. JETM
is a small and free library that is included in the monolith online shop, that
helps locating performance problems in existing Java applications. JETM enables
developers to track down performance issues on demand, either programmatic or
declarative with minimal impact on application performance, even in production.

![JETM Overview](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/performance-overview.png)
![JETM Request view](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/performance-request-view.png)

#### JMX
[JMX](http://en.wikipedia.org/wiki/Java_Management_Extensions) is a natural way to have access to technical management,
e.g. for tuning, statistics, log levels and so on. Unfortunately, it lacks a lightweight tool to expose mbeans and to browse
them securely on any application and environment without heavy infrastructure setup. [JMiniX](https://code.google.com/p/jminix/)
provides such a feature. You can reach JMiniX under the following url http://shop.monolith.io:8080/shop/jmx/.

![JMiniX](https://raw.githubusercontent.com/devops-dojo/the-app/master/external/images/jminix.png)

## Credits
A lot of this repository leverages the work from [Bernd Zuther](https://github.com/zutherb/).

## Contact

If you have any questions or remarks, please use the [issue tracker](https://github.com/devops-dojo/the-app/issues)

## Licensing

This work is open source, and is licensed under the Apache License, Version 2.0.
