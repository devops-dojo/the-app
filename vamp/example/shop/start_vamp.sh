#!/usr/bin/env bash
docker run -d \
    --net=host \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v $(which docker):/bin/docker \
    -v "/sys/fs/cgroup:/sys/fs/cgroup" \
    -e "DOCKER_HOST_IP=`docker-machine ip docker-vm`" \
    magneticio/vamp-docker:0.8.2-marathon
