# AppStash - Microservice Phone Shop Application

This example shows how to run the Microservice Phone Shop Application with docker.

![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/microservice-appserver.png)

## Install

If you want to run the example Microservice Phone Shop Application, you have to install the following components:

```
brew install docker boot2docker docker-compose
boot2docker init
```

## Run

[Compose](https://github.com/docker/compose) is a tool for defining and running complex applications with Docker. With
Compose, you define a multi-container application in a single file, then spin your application up in a single command
which does everything that needs to be done to get it running.

Our Microservice Phone Shop Application is one of those multi-container application. It contains of seven containers as
you can see in the following deployment diagram.

![Docker Deployment Diagram](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/deployment_diagramm_online_shop_docker.png)

Compose is great for development environments, staging servers, and CI. It isn't recommend to use it in production yet.

```
boot2docker start
#deploy variant a
docker-compose up
#deploy variant 4
docker-compose -f docker-compose-variant-b.yml up
```

[![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/microservice-appserver-youtube.png)](https://www.youtube.com/watch?v=EhPyYsZtn8o)
