#!/usr/bin/env bash
#docker run -d \
#           --net=host \
#           -v /var/run/docker.sock:/var/run/docker.sock \
#           -v $(which docker):/bin/docker \
#           -v "/sys/fs/cgroup:/sys/fs/cgroup" \
#           -e "DOCKER_HOST_IP=`docker-machine ip default`" \
#           magneticio/vamp-docker:0.8.0-marathon

curl -v -X POST --data-binary @shop.yml -H "Content-Type: application/x-yaml" http://192.168.99.100:8080/api/v1/deployments | gunzip -
